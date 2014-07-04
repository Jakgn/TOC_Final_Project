import org.json.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TocFinal {	
	public static void main(String[] args) throws Exception, JSONException {

		int I_len = 0, L_com = Integer.parseInt(args[2]);
		int C_idx[][] = new int[220][L_com];
		int Num_com=0;

		URL website = new URL(args[0]);
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8") );
		String inputLine;

		JSONObject item;
		String Sbuf, i1, i2, i3, i4, s1, s2, s3, s4;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int Lidx = 0, Lid = 0;

		String[] itm, its;
		String[] Iname = new String[11];
		String[] Ival = new String[11];
		int len_I;

		in.readLine();
		while ( (inputLine = in.readLine())!=null  ) 
		{
			if(Lidx==0){
				Lidx=1;
				int ln = inputLine.length();
				itm = inputLine.split(",");
				for(String itms : itm)
					I_len++;

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
				//System.out.println(">>"+Num_com+" I_len >>"+I_len);				

			}



			int ln = inputLine.length();
			inputLine = inputLine.substring(1, (ln-1)); //remove { and }
			itm = inputLine.split(",");
			len_I=0;

			for(String itms : itm)
			{
				its = itms.split(":",2);
				//System.out.println(its[0]+":"+its[1]);
				if(its[1].indexOf("\"")==-1 ){	// if no ""
					Ival[len_I] = its[1];
				}else{
					Ival[len_I] = its[1].substring(1, (its[1].length()-1));	
				}
				if(Lid ==0)
					Iname[len_I] = its[0].substring(1, (its[0].length()-1));
				len_I++;
			}
			Lid=1;



			for(int j=0;j<Num_com;j++)
			{
				if(L_com==2)
				{
					i1 = Iname[ C_idx[j][0] ];
					i2 = Iname[ C_idx[j][1] ];
					s1 = Ival[ C_idx[j][0] ];
					s2 = Ival[ C_idx[j][1] ];
					if(s1.length()==0 || s2.length()==0 )
						continue;
					Sbuf = i1+":"+s1+","+i2+":"+s2;
				}else if(L_com==3)
				{
					i1 = Iname[ C_idx[j][0] ];
					i2 = Iname[ C_idx[j][1] ];
					i3 = Iname[ C_idx[j][2] ];
					s1 = Ival[ C_idx[j][0] ];
					s2 = Ival[ C_idx[j][1] ];
					s3 = Ival[ C_idx[j][2] ];
					if(s1.length()==0 || s2.length()==0 || s3.length()==0 )
						continue;
					Sbuf = i1+":"+s1+","+i2+":"+s2+","+i3+":"+s3;
				}else	// L_com==4
				{
					i1 = Iname[ C_idx[j][0] ];
					i2 = Iname[ C_idx[j][1] ];
					i3 = Iname[ C_idx[j][2] ];
					i4 = Iname[ C_idx[j][3] ];
					s1 = Ival[ C_idx[j][0] ];
					s2 = Ival[ C_idx[j][1] ];
					s3 = Ival[ C_idx[j][2] ];
					s4 = Ival[ C_idx[j][3] ];
					if(s1.length()==0 || s2.length()==0 || s3.length()==0 || s4.length()==0 )
						continue;
					Sbuf = i1+":"+s1+","+i2+":"+s2+","+i3+":"+s3+","+i4+":"+s4;
				}
				if(map.containsKey(Sbuf) )
					map.put(Sbuf, (map.get(Sbuf) + 1) );
				else		
					map.put(Sbuf, 1);
			}
			if(in.readLine().equals("]") )
				break;
		}
		in.close();

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
			String ww = entry.getKey()+";"+entry.getValue();
			bw.write(ww);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}
