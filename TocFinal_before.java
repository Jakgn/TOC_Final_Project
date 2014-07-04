import org.json.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TocFinal {	
	public static void main(String[] args) throws Exception, JSONException {
		long time1, time2;
		time1 = System.currentTimeMillis();
		
		String tmp = "";
		FileReader fr = new FileReader("/home/jack/data6");
		BufferedReader br = new BufferedReader(fr);
/*		while(br.ready())
			tmp+=br.readLine();
		br.close();
*/

		String ItemName[] = {"鄉鎮市區", "交易標的", "土地區段位置或建物區門牌", "土地移轉總面積平方公尺", "都市土地使用分區","非都市土地使用分區", "非都市土地使用編定", "交易年月", "交易筆棟數", "移轉層次", "總樓層數", "建物型態", "主要用途", "主要建材", "建築完成年月", "建物移轉總面積平方公尺", "建物現況格局-房", "建物現況格局-廳", "建物現況格局-衛", "建物現況格局-隔間", "有無管理組織", "總價元", "單價每平方公尺", "車位類別", "車位移轉總面積平方公尺", "車位總價元" };
		int Itype[] = {0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 2, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1};	// 0 String, 1 int , 2 double
		int HaveItem[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int I_len = 0, L_com = Integer.parseInt(args[2]);
		int C_idx[][] = new int[220][L_com];
		int Num_com=0;// int Num_com=1;
        	
 /*       URL website = new URL(args[0]);
       	URLConnection connection = website.openConnection();
     	BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8") );
 */      	String inputLine;
        	
    	JSONObject item;
		String Sbuf, i1, i2, i3, i4, s1, s2, s3, s4;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int Lidx = 0;
	
		br.readLine();
        while ( (inputLine = br.readLine())!=null  ) {
      //  	if( (Lidx++)%2==1 )
       // 	{
				
				Lidx++;
        		item = new JSONObject(inputLine); // can combine up?
        			
        		if(Lidx==1){
					for(int i=0;i<26;i++)
						if(item.opt(ItemName[i])!=null)		//check if the name is in the key of object
							HaveItem[I_len++]=i;
					if(L_com==2){
						for(int i=0;i<=I_len-L_com;i++)
							for(int j=i+1;j<=I_len-L_com+1;j++)
							{
								C_idx[Num_com][0] = i;
								C_idx[Num_com++][1] = j;
							}
					}else if(L_com==3){
						for(int i=0;i<=I_len-L_com;i++)
							for(int j=i+1;j<=I_len-L_com+1;j++)
								for(int k=j+1;k<=I_len-L_com+2;k++)
								{
									C_idx[Num_com][0] = i;
									C_idx[Num_com][1] = j;
									C_idx[Num_com++][2] = k;
								}
					}else if(L_com==4){
						for(int i=0;i<=I_len-L_com;i++)
							for(int j=i+1;j<=I_len-L_com+1;j++)
								for(int k=j+1;k<=I_len-L_com+2;k++)
									for(int m=k+1;m<=I_len-L_com+3;m++)
									{
										C_idx[Num_com][0] = i;
										C_idx[Num_com][1] = j;
										C_idx[Num_com][2] = k;
										C_idx[Num_com++][3] = m;
									}	
					}
				//	System.out.println(">>"+Num_com);				
        			
        		}
      			for(int j=0;j<Num_com;j++)
				{
					if(L_com==2)
					{
						i1 = ItemName[ HaveItem[ C_idx[j][0] ] ];
						i2 = ItemName[ HaveItem[ C_idx[j][1] ] ];
						s1 = item.optString(i1, "null");
						s2 = item.optString(i2, "null"); 
						if(s1.length()==0 || s2.length()==0 )
							continue;
						Sbuf = i1+":"+s1+","+i2+":"+s2;
					}else if(L_com==3)
					{
						i1 = ItemName[ HaveItem[ C_idx[j][0] ] ];
						i2 = ItemName[ HaveItem[ C_idx[j][1] ] ];
						i3 = ItemName[ HaveItem[ C_idx[j][2] ] ];
						s1 = item.optString(i1, "null");
						s2 = item.optString(i2, "null"); 
						s3 = item.optString(i3, "null");
						if(s1.length()==0 || s2.length()==0 || s3.length()==0 )
							continue;
						Sbuf = i1+":"+s1+","+i2+":"+s2+","+i3+":"+s3;
					}else	// L_com==4
					{
						i1 = ItemName[ HaveItem[ C_idx[j][0] ] ];
						i2 = ItemName[ HaveItem[ C_idx[j][1] ] ];
						i3 = ItemName[ HaveItem[ C_idx[j][2] ] ];
						i4 = ItemName[ HaveItem[ C_idx[j][3] ] ];
						s1 = item.optString(i1, "null");
						s2 = item.optString(i2, "null"); 
						s3 = item.optString(i3, "null");
						s4 = item.optString(i4, "null");
						if(s1.length()==0 || s2.length()==0 || s3.length()==0 || s4.length()==0 )
							continue;
						Sbuf = i1+":"+s1+","+i2+":"+s2+","+i3+":"+s3+","+i4+":"+s4;
					}
					if(map.containsKey(Sbuf) )
						map.put(Sbuf, (map.get(Sbuf) + 1) );
					else		
						map.put(Sbuf, 1);
				}
				if(br.readLine().equals("]") )
					break;
//			}	
		}
     //   in.close();
		br.close();	

		List<Map.Entry<String, Integer> > DataList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(DataList, new Comparator<Map.Entry<String, Integer>>()
		{
			public int compare(Map.Entry<String, Integer> entry1,
								Map.Entry<String, Integer> entry2)
			{return (entry2.getValue() - entry1.getValue());}
		});
		int cnt=1, Vtmp=0;
		
		int Top = Integer.parseInt(args[1]);
		BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
		for(Map.Entry<String, Integer> entry:DataList)
		{
			
			if(cnt==Top) Vtmp = entry.getValue();
			if( (cnt++)>Top && entry.getValue()!=Vtmp) 
				break;
			
			bw.write(entry.getKey()+";"+entry.getValue() );
			bw.newLine();
		}
		bw.flush();
		bw.close();
		time2 = System.currentTimeMillis();
		System.out.println("time: "+(time2-time1));
	}
}
