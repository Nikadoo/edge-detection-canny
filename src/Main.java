import java.util.*;
import java.io.*;

public
	class Main {
	
	public static final int X = 512;

	public static void main(String[] args){
		int[] data = getData("/inData.ppm");
		//System.out.println(data.length);
		
		
		int[][] tab = new int [X][X];
		int l=0;
		
		//Przypisane do tablicy dwuwymiarowej
		for(int i=0;i<X;i++)
		{
			for(int j=0;j<X;j++)
			{
				tab[i][j]=data[l];
				l++;
			}
		}
		
		int min=512/3+1;
		int indeks=0;
		int ilelini=0;
		
		for(int i=0;i<X;i++)
		{
			for(int j=0;j<X-min;j++) //linie poziome
			{
				int ilosczer=0;
				if(tab[i][j]>0) //szukamy punktu początkowego lini
				{
					
					if(j+min>=512)
					{
						indeks=512;
					}
					else indeks=j+min;
					if(tab[i][indeks]>0) //czy ostatni punkt nalezy do prostej
					{
						for(int n=j+1;n<indeks;n++)
						{
							if(tab[i][n]==0) //sprawdzamy czy pomiedzy punktami sa przerwy
							{
								ilosczer++;
								
								if(ilosczer>0)
								{
									n=indeks; //jezeli tak to powodujemy skonczenie tablicy
								}
							}
						}
						if(ilosczer==0) //jezeli brak przerw to zamieniamy na 64
						{
							ilelini++;
							for(int n=j;n<512;n++) //zamieniamy całą linię na 64
							{
								if(tab[i][n]>0)
								{
									tab[i][n]=64;
									
								}else n=512;
							}
						}
					}
				}
			}
			
		}
		for(int i=0;i<X;i++) //linie pionowe
		{
			for(int j=0;j<X-min;j++) //linie poziome
			{
				int ilosczer=0;
				if(tab[j][i]>0) //szukamy punktu początkowego lini
				{
					
					if(j+min>=512)
					{
						indeks=512;
					}
					else indeks=j+min;
					if(tab[indeks][i]>0) //czy ostatni punkt nalezy do prostej
					{
						for(int n=j+1;n<indeks;n++)
						{
							if(tab[n][i]==0) //sprawdzamy czy pomiedzy punktami sa przerwy
							{
								ilosczer++;
								
								if(ilosczer>0)
								{
									n=indeks; //jezeli tak to powodujemy skonczenie tablicy
								}
							}
						}
						if(ilosczer<10) //jezeli brak przerw to zamieniamy na 64 
						/*        ustawiłam <10 jako tolerancję dla przerywanych lini pionowych
                         na obrazku znajduje się szkło, które zawiera linie pionowe jak i poziome jednak filtr Canniego nie uwzględnia ich jako prostych	*/
						{
							ilelini++;
							for(int n=j;n<512;n++) //zamieniamy całą linię na 64
							{	
								if(tab[n][i]>0)
								{
									tab[n][i]=64;
									
									
								}else n=512;
							}
						}
					}
				}
			}
			}
		
		
		for(int i=0;i<X;i++)
		{
			for(int j=0;j<X;j++)
			{
				if(tab[i][j]!=64) 
				{
					tab[i][j]=0; //zerujemy tablice
				}
				
			}
		}
		for(int i=0;i<X;i++)
		{
			for(int j=0;j<X;j++) //linia pozioma
			{
				if(tab[i][j]==64) //jezeli jest linia
				{
					for(int n=j;n<X;n++)
					{
						tab[i][j]=128; //przedluzamy ja i wpisujemy wartosc 128
					}
					j=X; //i konczymy tablice;
					
				}
				
			}
			for(int j=0;j<X;j++) //linia pionowa
			{
				if(tab[j][i]==128) //gdy poczatek pionowej zaczyna się w lini poziomej
				{
					if(tab[j+1][i]==64) 
					{
						tab[j][i]=225; //oznaczam jako punkt przecięcia
						j++; 
					}
				}
				if(tab[j][i]==64) //jezeli istnieje linia pionowa z początkiem lub srodkiem 64
				{
					for(int n=j;n<X;n++)
					{
						if(tab[j][i]==128)
						{tab[j][i]=225;} //punkt przecięcia
					    else tab[j][i]=128;
					
					}
				
					j=X; //i konczymy tablice;
					
				}
				
			}
		}
		
	}
	
	public static int[] getData(String path){
		try{
			Scanner scanner = new Scanner(new File(path));
			int [] data = new int [X*X];
			int i = 0;
			while(scanner.hasNextInt()){
			   data[i++] = scanner.nextInt();
			   scanner.nextInt();
			   scanner.nextInt();
			}
			return data;
		}catch(Exception io){
			System.out.println(io);
			return null;
		}
	}	
	
}
