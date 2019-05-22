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
	String[] RegSensLastSeen = new String[40];
    int UniqueEPCount = 0;
    int RegListCount = 0;
    boolean UniqueEPCTag = false;
    boolean UniqueRegTag = false;
    boolean conn = false;
    Calendar CurTime;
    
    static String tcpParam = "192.168.1.116:9090";
    static String commParam = "COM4:115200";
    
    public void saveSensList() {
    	RegSensList = UniqueEPC;
    	
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

	@Override
	public void OutPutEPC(Tag_Model model) {
		UniqueEPCTag = true;
		
		CurTime = Calendar.getInstance();
		CurTime.setTime(new Date());
		String timeStr = String.format("%d:%02d:%02d", CurTime.get(Calendar.HOUR), CurTime.get(Calendar.MINUTE),CurTime.get(Calendar.SECOND));
		
		for(int index = 0; index < UniqueEPC.length; ++index)
	            if (model._EPC.equals(UniqueEPC[index])) {
	            	RegSensLastSeen[index] = timeStr;
	                UniqueEPCTag = false;
	            }
		if (UniqueEPCTag){
			 UniqueEPC[UniqueEPCount] = model._EPC;
			TagLastSeen[UniqueEPCount] = timeStr;
			 UniqueEPCount++;
	    }
		
		if (RegListCount != 0) {
			for(int index = 0; index < RegSensList.length; ++index)
				if (model._EPC.equals(RegSensList[index])) {
					TagLastSeen[index] = timeStr;
					UniqueRegTag = false;
				}
			if (UniqueEPCTag){
				UniqueEPC[UniqueEPCount] = model._EPC;
				TagLastSeen[UniqueEPCount] = timeStr;
				UniqueEPCount++;
			}
		}
	}
}
