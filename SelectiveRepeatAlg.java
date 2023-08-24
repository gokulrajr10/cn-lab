import java.io.*;
public class SelectiveRepeatAlg
{
int intToPkt,intWinSize,intPktNo,intPCount;
public SelectiveRepeatAlg()throws Exception
{
BufferedReader br=new BufferedReader(new InputStreamReader(new
DataInputStream(System.in)));
System.out.println("Enter the window size");
intWinSize=Integer.parseInt(br.readLine());
System.out.println("enter Total no. of packets");
intToPkt=Integer.parseInt(br.readLine());
System.out.println("Do you want to kill any Packets:(y/n)");
String strAns=br.readLine();
if(strAns.equals("y"))
{
System.out.println("Enter NO.of Packets 2 kill");
intPktNo=Integer.parseInt(br.readLine());
intPCount=1;
}}
public void sRepeat(int pn)throws Exception
{
System.out.println("------------------------------");
System.out.println("Selective Repeats of packets"+pn);
System.out.println("-------------------------------");
Thread.sleep(1000);
System.out.println("Packet "+(pn)+("send==>"));
Thread.sleep(1000);
System.out.println("Packet"+(pn)+("received==>"));
Thread.sleep(2000);
System.out.println("ACK"+(pn)+" Received");
}
public int calculate(int cos,int pno)
{
int n;
if(cos>pno)
n=intWinSize-(intPktNo%intWinSize);
else
n=intWinSize-intPktNo;
return n;
}
public void trans()throws Exception
{
int intC,intCount=0;
while(intPCount<intToPkt)
{
if(intCount==intWinSize)
{
System.out.println("Next Session");
intCount=0;
}
if(intPktNo==intPCount)
{
System.out.println("Packet"+intPktNo+"discard---->");
Thread.sleep(2000);
intPCount++;
intC=calculate(intWinSize,intPktNo);
System.out.println("Remaining Packet to be sent in the fashion"+intC);
for(int j=0;j<=intC;j++)
{
Thread.sleep(1000);
System.out.println("Packet "+intPCount+"Sent==>");
Thread.sleep(1000);
System.out.println("\t\tPacket"+intPCount+" Received");
Thread.sleep(2000);
System.out.println("ACK"+intPCount+"Received");
intPCount++;
intCount=0;
}
sRepeat(intPktNo);
Thread.sleep(2000);
System.out.println("next Session");
intCount=0;
}
else{
Thread.sleep(1000);
System.out.println("Packet"+intPCount+"send==>");
Thread.sleep(1000);
System.out.println("\t\t packet"+intPCount+"Received");
intPCount++;
}
intPCount++;
}}
public static void main(String agr[])throws Exception
{
SelectiveRepeatAlg obSRA=new SelectiveRepeatAlg();
obSRA.trans();
}}
