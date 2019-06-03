package com.smartrfid.sportid;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import java.util.Calendar;
import java.util.Date;

public class ReaderController implements IAsynchronousMessage {

	String[] UniqueEPC = new String[40];
	String[] TagLastSeen = new String[40];
	String[] RegSensList = new String[40];
	String[] RegCurrList = new String[40];
	String[] RegCurrLastSeen = new String[40];
	
    int UniqueEPCount = 0;
    int RegListCount = 0;
    int SensListCount = 0;
    boolean UniqueEPCTag = false;
    boolean UniqueRegTag = false;
    boolean registration = false;
    boolean conn = false;
    Calendar CurTime;
    int[] CurrTime = new int[3];
    int[] CheckTime = new int[3];
    int[] PassedTime = new int[3];
    int[] ParsedTime = new int[3];
    
    static String tcpParam = "192.168.1.116:9090";
    static String commParam = "COM4:115200";
    
    public boolean isConnected() {
    	return conn;
    }
    
    public void saveSensList() {
    	RegSensList = UniqueEPC;
    	for(int index = 0; index < RegSensList.length; ++index) 
    		System.out.println("RegSensList"+ RegSensList[index]);
    	SensListCount = UniqueEPCount;
    	System.out.println("SensListCount"+ SensListCount);
    	registration = true;
    }
    
    //Отдать епц метки из листа с ласт син 2 сек метками
    public String getRegEPC(int num) {
    	if (RegListCount < num) {return null;}
    	else return RegCurrList[num];
    }
    
    public String getRegTimestamp(int num) {
    	if (RegListCount < num) {return null;}
    	else return RegCurrLastSeen[num];
    }
    
    public int getRegListCount() {
    	return RegListCount;
    }
    
    public ReaderController() {
    	//TCPConnect();
    }
    
    public void startReader() {
    	COMConnect();
    }
    
    public void TCPDisconnect(){
    	if  (conn != false) {
    	CLReader.Stop(commParam);
    	CLReader.CloseConn(commParam);
    	conn = false;
    	}
    }
    
    public boolean TCPConnect(){
    	if (conn != true) {
	        conn = CLReader.CreateTcpConn(tcpParam,this);
	        try {
	            if(conn){
	                System.out.println("connection success...");
	                CLReader._Tag6C.GetEPC_TID(tcpParam, 1, 1);
	                return true;
	            } else {
	                System.out.println("connection failure!");
	                return false;
	            }
	        } catch (Exception e) {
	            System.out.println("Exception " + e.getMessage());
	            return false;
	        }
    	} else return false;
    }
    
    
    public boolean COMConnect() {
    	if (conn != true) {
    	conn = CLReader.CreateSerialConn(commParam,this);
    	try {
    		if(conn){
    			System.out.println("connection success...");
    			CLReader._Tag6C.GetEPC_TID(commParam, 1, 1);
    			//CLReader._Config.SetEPCBaseBandParam(connID, basebandMode, qValue, session, searchType)
    			return true;
    		} else {
    			System.out.println("connection failure!");
    			return false;
    		}
    		} catch (Exception e) {
    			System.out.println("Exception " + e.getMessage());
    			return false;
    		}
    	} else return false;
    }
    
    public void resetEPC() { 	
    	for (int index = 0; index < UniqueEPCount; index++) {
    		UniqueEPC[index] = "";	
		}
    	UniqueEPCount = 0;
    }
    
    public String getEPC(int num) {
    	if (UniqueEPCount < num) {return null;}
    	else return UniqueEPC[num];
    }
    
    public String getLastTimestamp(int num) {
    	if (UniqueEPCount < num) {return null;}
    	else return TagLastSeen[num];
    }
    
    public int getUniqueTagCount() {
    	return UniqueEPCount;
    }
	
//	public static void main(String[] args) {
//	        new ReaderController();
//	}
    
    public void checkExpired() {
		boolean expired = false;
		int expiredNum = 0;	
		if (RegListCount > 0) {
			CurTime = Calendar.getInstance();
			CurTime.setTime(new Date());
			CheckTime[0] = CurTime.get(Calendar.HOUR_OF_DAY);
			CheckTime[1] = CurTime.get(Calendar.MINUTE);
			CheckTime[2] = CurTime.get(Calendar.SECOND);
			
			for(int index = 0; index < RegListCount; ++index) {
				String[] pairs = RegCurrLastSeen[index].split(":");			
				for (int i = 0; i < pairs.length; i++) {
					ParsedTime[0] = Integer.parseInt(pairs[0]);
					ParsedTime[1] = Integer.parseInt(pairs[1]);
					ParsedTime[2] = Integer.parseInt(pairs[2]);
				}
				PassedTime[0] = CheckTime[0] - ParsedTime[0];
				PassedTime[1] = CheckTime[1] - ParsedTime[1];
				PassedTime[2] = CheckTime[2] - ParsedTime[2];
				if (PassedTime[1] < 0) {
					PassedTime[0]--;
					PassedTime[1]=PassedTime[1]+60;
					if (PassedTime[2] < 0) {
						PassedTime[1]--;
						PassedTime[2]=PassedTime[2]+60;
					}
				}
				if (PassedTime[0]>0 || PassedTime[1]>0 || PassedTime[2]>2){
					expired = true;
					expiredNum = index;
				}
			}
			if (expired) {
				RegCurrList[RegListCount] = "";
				RegCurrLastSeen[RegListCount] = "";
				RegListCount--;
				for(int index = expiredNum; index < RegCurrList.length-1; ++index) {
					RegCurrList[index] = RegCurrList[index+1];
					RegCurrLastSeen[index] = RegCurrLastSeen[index+1];
				}
			}
		}
    }

	@Override
	public void OutPutEPC(Tag_Model model) {
		UniqueEPCTag = true;
		UniqueRegTag = true;
		boolean InSensList = false;
		
		CurTime = Calendar.getInstance();
		CurTime.setTime(new Date());
		CurrTime[0] = CurTime.get(Calendar.HOUR_OF_DAY);
		CurrTime[1] = CurTime.get(Calendar.MINUTE);
		CurrTime[2] = CurTime.get(Calendar.SECOND);
		String timeStr = String.format("%d:%02d:%02d",CurrTime[0],CurrTime[1],CurrTime[2]);
		
		for(int index = 0; index < UniqueEPC.length; ++index)
	            if (model._EPC.equals(UniqueEPC[index])) {
	            	TagLastSeen[index] = timeStr;
	                UniqueEPCTag = false;
	            }
		if (UniqueEPCTag){
			UniqueEPC[UniqueEPCount] = model._EPC;
			TagLastSeen[UniqueEPCount] = timeStr;
			UniqueEPCount++;
	    }
		
		if (registration) {
			for(int index2 = 0; index2 < SensListCount; ++index2)
				if (model._EPC.equals(RegSensList[index2])) {
					InSensList = true;
				}
			if (InSensList)
			for(int index = 0; index < RegListCount; ++index)	
				if (model._EPC.equals(RegCurrList[index])) {
					RegCurrLastSeen[index] = timeStr;
					UniqueRegTag = false;
				}
			if (UniqueRegTag & InSensList){
				RegCurrList[RegListCount] = model._EPC;
				RegCurrLastSeen[RegListCount] = timeStr;
				RegListCount++;
			}
		}
	}
}
