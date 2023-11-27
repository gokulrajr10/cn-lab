import java.io.*;
public class Bitstuffing
{
    public static void main(String args[]) throws IOException
    {
        int i = 0, x;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String sy = "01111110", sx;
        StringBuilder sby = new StringBuilder(sy);
        System.out.println("enter the data:");
        sx = br.readLine();
        StringBuilder sbx = new StringBuilder(sx);
        x = sx.length();
        while (i + 5 <= x)
        {
            String s1 = sx.substring(i, i + 5);
            if (check(s1))
            {
                sbx.insert(i + 5, 0);
                i = i + 6;
            } else
                i++;
        }
        System.out.println("bit stuffing:");
        System.out.println(sbx);
        System.out.println("final output:");
        System.out.println(sby + " " + sbx + " " + sby);
        System.out.println(sby.append(sbx.append(sby)));
    }
    private static boolean check(String s)
    {
        String s1 = "11111";
        if (s.equals(s1))
            return true;
        else return false;
    }
}