package com.smartrfid.sportid;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.Protocol.Tag_Model;

public class ReaderController implements IAsynchronousMessage {

	String[] UniqueEPC = new String[40];
    int UniqueEPCount = 0;
    boolean UniqueEPCTag = false;
    boolean conn = false;
    
    static String tcpParam = "192.168.1.116:9090";
    static String commParam = "COM3:115200";
    
    public ReaderController() {
    	//TCPConnect();
    	
    }
    
    public void startReader() {
    	COMConnect();
    }
    
    public void TCPDisconnect(){
    	CLReader.Stop(commParam);
    	CLReader.CloseConn(commParam);
    }
    
    public boolean TCPConnect(){

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
    }
    
    
    public boolean COMConnect() {
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
    
    public int getUniqueTagCount() {
    	return UniqueEPCount;
    }
	
	public static void main(String[] args) {
	        new ReaderController();
	}

	@Override
	public void OutPutEPC(Tag_Model model) {
		UniqueEPCTag = true;
		for(int index = 0; index < UniqueEPC.length; ++index)
	            if (model._EPC.equals(UniqueEPC[index])) {
	                UniqueEPCTag = false;
	            }
		if (UniqueEPCTag){
			 UniqueEPC[UniqueEPCount] = model._EPC;
			 UniqueEPCount++;
			 
	    }
	}
}
