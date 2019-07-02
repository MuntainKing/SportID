package com.smartrfid.sportid;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import java.util.Calendar;
import java.util.Date;

public class ReaderController implements IAsynchronousMessage {
	static final int CompetCountConstraint = 40;
	static final int ControlPointCountConstraint = 10;
	int CompetLeaveTime = 10;

	String[] UniqueEPC = new String[CompetCountConstraint];
	String[] TagLastSeen = new String[CompetCountConstraint];
	String[] RegSensList = new String[CompetCountConstraint];
	String[] RegCurrList = new String[CompetCountConstraint];
	String[] RegCurrLastSeen = new String[CompetCountConstraint];

	Competitor[] competitors = new Competitor[CompetCountConstraint];
	String[] ContestList = new String[CompetCountConstraint];
	int TagOffTimeS = 20;

	int CompetitorsCount;	
	String[][] ContestTimestamps = new String[CompetCountConstraint][ControlPointCountConstraint];
	String[] CompetLastSeen = new String[CompetCountConstraint];

	int UniqueEPCount = 0;
	int RegListCount = 0;
	int SensListCount = 0;
	boolean UniqueEPCTag = false;
	boolean UniqueRegTag = false;
	boolean registration = false;
	boolean contest = false;
	boolean conn = false;
	Calendar CurTime;

	int[] CompetStartTime = new int[4];
	int[] PassedChP = new int[CompetCountConstraint];

	int[] CurrTime = new int[4];
	int[] CheckTime = new int[3];
	int[] PassedTime = new int[3];
	int[] ParsedTime = new int[3];
	int[] PassedCompetTime = new int[4];
	int[][] StartTime = new int[CompetCountConstraint][4];

	int[] ResultTableIndex = new int[CompetCountConstraint];
	String[] ResultTableTime = new String[CompetCountConstraint];
	int finalists = 0;

	static String tcpParam = "192.168.0.116:9090";
	static String commParam = "COM4:115200";

	String TimerString;
	int CheckPoints;
	int CompFoundIndex;


	public void setCheckPoints(int checkPoints) {
		CheckPoints = checkPoints;
	}

	public String getTimerString() {

		Calendar CurTime = Calendar.getInstance();
		CurTime.setTime(new Date());

		int[] CurrTime = new int[4];
		CurrTime[0] = CurTime.get(Calendar.HOUR_OF_DAY);
		CurrTime[1] = CurTime.get(Calendar.MINUTE);
		CurrTime[2] = CurTime.get(Calendar.SECOND);
		CurrTime[3] = CurTime.get(Calendar.MILLISECOND);		

		int[] PassedCompetTime = new int[4];
		PassedCompetTime[0] = CurrTime[0] - CompetStartTime[0]; 
		PassedCompetTime[1] = CurrTime[1] - CompetStartTime[1];
		PassedCompetTime[2] = CurrTime[2] - CompetStartTime[2];
		PassedCompetTime[3] = CurrTime[3] - CompetStartTime[3];

		if (PassedCompetTime[3] < 0) {
			PassedCompetTime[2]--;
			PassedCompetTime[3]=PassedCompetTime[3]+1000;
		}
		if (PassedCompetTime[1] < 0) {
			PassedCompetTime[0]--;
			PassedCompetTime[1]=PassedCompetTime[1]+60;
		}
		if (PassedCompetTime[2] < 0) {
			PassedCompetTime[1]--;
			PassedCompetTime[2]=PassedCompetTime[2]+60;
		}
		return String.format("%d:%02d:%02d:%03d",PassedCompetTime[0],PassedCompetTime[1],PassedCompetTime[2],PassedCompetTime[3]);

	}

	public void initChP() {
		for(int index = 0; index < PassedChP.length; ++index) PassedChP[index] = 0;
	}

	public boolean isCompetitionStarted() {
		return contest;
	}

	public String[][] getTimeStamps(){
		return ContestTimestamps;
	}

	public void stopCompetition() {
		contest = false;
	}

	public void startCompetition() {
		contest = true;
		registration = false;
		CurTime = Calendar.getInstance();
		CurTime.setTime(new Date());
		CompetStartTime[0] = CurTime.get(Calendar.HOUR_OF_DAY);
		CompetStartTime[1] = CurTime.get(Calendar.MINUTE);
		CompetStartTime[2] = CurTime.get(Calendar.SECOND);
		CompetStartTime[3] = CurTime.get(Calendar.MILLISECOND);
	}

	public int getFinalistCount() {
		return finalists;
	}

	public String[][] getFinalists(){
		String[][] FinalistStr = new String[CompetCountConstraint][2];
		int[] TimeWeights = new int[finalists];

		// парсим все строки 
		// финалистс - количество финалистов
		for (int index = 0; index < finalists; index++) { 		
			// парсим строку с временем каждого финалиста
			String[] data = ResultTableTime[index].split(":");
			int[] dataI = new int[data.length];
			for(int index2 = 0; index2 < data.length; ++index2) dataI[index2] = Integer.parseInt(data[index2]);
			TimeWeights[index] = dataI[0]*3600000 + dataI[1]*60000 + dataI[2]*1000 + dataI[3];
		}

		boolean flag = false;
		int tempWeigth = 0;
		int tempIndex;
		String tempTime;

		//сортируем
		for (int j = 1; j < finalists-1; j++) {
			flag = false;
			for (int i = 0; i < finalists-j; i++) {

				if (TimeWeights[i] > TimeWeights[i+1]) {
					tempWeigth = TimeWeights[i];
					TimeWeights[i] = TimeWeights[i+1];
					TimeWeights[i+1] = tempWeigth;
					tempIndex = ResultTableIndex[i];
					ResultTableIndex[i] = ResultTableIndex[i+1];
					ResultTableIndex[i+1] = tempIndex;
					tempTime = ResultTableTime[i];
					ResultTableTime[i] = ResultTableTime[i+1];
					ResultTableTime[i+1] = tempTime;   				
					flag = true;
				}
			}
			if (flag == false) break;
		}

		// формируем таблицу финалистов [индекс][время]

		for (int j = 0; j < CompetCountConstraint; j++) {
			FinalistStr[j][0] = Integer.toString(ResultTableIndex[j]);
			FinalistStr[j][1] = ResultTableTime[j];
		}
		return FinalistStr;
	}


	public boolean isConnected() {
		return conn;
	}

	public void saveSensList() {
		RegSensList = UniqueEPC;
		SensListCount = UniqueEPCount;
		registration = true;
	}

	public void setCompetitors(Competitor[] competitors) {
		this.competitors = competitors;
	}

	public void setCompetCount(int CC) {
		CompetitorsCount = CC;
	}

	public void newCompet() {
		for (int j = 0; j < CompetCountConstraint; j++) {
			PassedChP[j] = 0;
			ResultTableIndex[j] = 0;
			ResultTableTime[j] = null;
			for (int i = 0; i < ControlPointCountConstraint; i++) {
				ContestTimestamps[j][i] = null;
			}
		}
		finalists = 0;
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
			CLReader.Stop(tcpParam);
			CLReader.CloseConn(tcpParam);
			conn = false;
		}
	}

	public boolean TCPConnect(){
		if (conn != true) {
			conn = CLReader.CreateTcpConn(tcpParam,this);
			try {
				if(conn){
					System.out.println("CHECK.");
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

	public void COMDisconnect() {
		if  (conn != false) {
			CLReader.Stop(commParam);
			CLReader.CloseConn(commParam);
			conn = false;
		}
	}

	public boolean COMConnect() {
		if (conn != true) {
			conn = CLReader.CreateSerialConn(commParam,this);
			try {
				if(conn){
					System.out.println("connection success...");
					CLReader._Tag6C.GetEPC_TID(commParam, 15, 1);
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

	public void chNetwork() {
		System.out.println("netw");
		CLReader._Config.SetReaderNetworkPortParam(commParam, "192.168.0.116", "255.255.255.0", "192.168.0.1");
	}

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
		
		System.out.println("ANTENNA NUMBER = "+model._ANT_NUM);

		CurTime = Calendar.getInstance();
		CurTime.setTime(new Date());
		CurrTime[0] = CurTime.get(Calendar.HOUR_OF_DAY);
		CurrTime[1] = CurTime.get(Calendar.MINUTE);
		CurrTime[2] = CurTime.get(Calendar.SECOND);
		CurrTime[3] = CurTime.get(Calendar.MILLISECOND);
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
		if (contest) {
			for(int index = 0; index < CompetitorsCount; ++index)	
				if (model._EPC.equals(competitors[index].EPC)) {
					CompFoundIndex = index; // найденная метка принадлежит участнику


					PassedCompetTime[0] = CurrTime[0] - CompetStartTime[0]; 
					PassedCompetTime[1] = CurrTime[1] - CompetStartTime[1];
					PassedCompetTime[2] = CurrTime[2] - CompetStartTime[2];
					PassedCompetTime[3] = CurrTime[3] - CompetStartTime[3];

					if (PassedCompetTime[3] < 0) {
						PassedCompetTime[2]--;
						PassedCompetTime[3]=PassedCompetTime[3]+1000;
					}
					if (PassedCompetTime[2] < 0) {
						PassedCompetTime[1]--;
						PassedCompetTime[2]=PassedCompetTime[2]+60;
					}
					if (PassedCompetTime[1] < 0) {
						PassedCompetTime[0]--;
						PassedCompetTime[1]=PassedCompetTime[1]+60;
					}
					String PasstimeStr = String.format("%d:%02d:%02d:%03d",PassedCompetTime[0],PassedCompetTime[1],PassedCompetTime[2],PassedCompetTime[3]);
					// Нашли время, прошедшее со старта

					//System.out.println("PasstimeStr "+ PasstimeStr);
					//System.out.println("ContestTimestamps[index][0] "+ContestTimestamps[index][0]);
					//System.out.println("CPassedChP[index] "+PassedChP[index]);
					if (PassedChP[CompFoundIndex] < CheckPoints) //если участник ещё не финишировал
						if (ContestTimestamps[CompFoundIndex][0] == null) {   //если проходим старт
							ContestTimestamps[CompFoundIndex][0] = PasstimeStr;
							PassedChP[CompFoundIndex]++;

							StartTime[CompFoundIndex][0] = PassedCompetTime[0];
							StartTime[CompFoundIndex][1] = PassedCompetTime[1];
							StartTime[CompFoundIndex][2] = PassedCompetTime[2];
							StartTime[CompFoundIndex][3] = PassedCompetTime[3];
						}
						else {

							//Находим время от начала забега до момента, когда последний раз видели метку
							String lastSeen = CompetLastSeen[CompFoundIndex];
							String[] data = lastSeen.split(":");
							int[] dataI = new int[data.length];
							for(int index2 = 0; index2 < data.length; ++index2) dataI[index2] = Integer.parseInt(data[index2]);
							int[] NewTS = new int[4];
							int[] FinishTime = new int[4];
							NewTS[0] = PassedCompetTime[0] - dataI[0];
							NewTS[1] = PassedCompetTime[1] - dataI[1];
							NewTS[2] = PassedCompetTime[2] - dataI[2];
							NewTS[3] = PassedCompetTime[3] - dataI[3];

							if (NewTS[3] < 0) {
								NewTS[2]--;
								NewTS[3]=NewTS[3]+1000;
							}
							if (NewTS[2] < 0) {
								NewTS[1]--;
								NewTS[2]=NewTS[2]+60;
							}
							if (NewTS[1] < 0) {
								NewTS[0]--;
								NewTS[1]=NewTS[1]+60;
							}

							if (NewTS[0]>0 || NewTS[1]>0 || NewTS[2]>CompetLeaveTime) {
								ContestTimestamps[CompFoundIndex][PassedChP[CompFoundIndex]] = PasstimeStr;
								PassedChP[CompFoundIndex]++;

								if (PassedChP[CompFoundIndex] == CheckPoints)  {

									FinishTime[0] = PassedCompetTime[0] - StartTime[CompFoundIndex][0];
									FinishTime[1] = PassedCompetTime[1] - StartTime[CompFoundIndex][1];
									FinishTime[2] = PassedCompetTime[2] - StartTime[CompFoundIndex][2];
									FinishTime[3] = PassedCompetTime[3] - StartTime[CompFoundIndex][3];

									if (FinishTime[3] < 0) {
										FinishTime[2]--;
										FinishTime[3]=FinishTime[3]+1000;
									}
									if (FinishTime[2] < 0) {
										FinishTime[1]--;
										FinishTime[2]=FinishTime[2]+60;
									}
									if (FinishTime[1] < 0) {
										FinishTime[0]--;
										FinishTime[1]=FinishTime[1]+60;
									}

									String FinishString = String.format("%d:%02d:%02d:%03d",FinishTime[0],FinishTime[1],FinishTime[2],FinishTime[3]);

									ResultTableIndex[finalists] = CompFoundIndex;
									ResultTableTime[finalists] = FinishString;
									finalists++;
									System.out.println("Финалист "+ CompFoundIndex + " время " + FinishString);
								}
							}
						} //else { ContestTimestamps[index][PassedChP[index]] = PasstimeStr; PassedChP[index]++;}

					CompetLastSeen[CompFoundIndex] = PasstimeStr;
					//System.out.println("CompetLastSeen[index] "+ CompetLastSeen[index]);
				}
		}
	}
}
