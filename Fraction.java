package fractions;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
public class Fraction {
	private int numerator;
	private int denominator;
	public Fraction(int n, int d){
		numerator = n;
		denominator = d;
	}
	@Override
	public String toString(){
		if (numerator == 0){
			return ("0");
		}
		if (denominator ==1){
			return (Integer.toString(numerator));
		}
		else{ String s = Integer.toString(numerator) + "/" + Integer.toString(denominator);
		return (s);}
	}
	public static String fracToString(Fraction f){
		if (f == null){
			return "Ошибка в выражении!";
		}
		if (f.numerator == 0){
			return ("0");
		}
		if (f.denominator ==1){
			return (Integer.toString(f.numerator));
		}
		else{ String s = Integer.toString(f.numerator) + "/" + Integer.toString(f.denominator);
		return (s);}
	}
	public Fraction plus (Fraction f2){
		Fraction r = new Fraction ((numerator*f2.denominator) + 
				(f2.numerator*denominator) ,
				denominator*f2.denominator);
		return r;
	}
	public Fraction minus(Fraction f2){
		Fraction r = new Fraction((numerator*f2.denominator) - 
				(f2.numerator*denominator) ,
				denominator*f2.denominator);
		return r;
	}
	public Fraction multiply(Fraction f2){
		Fraction r = new Fraction(numerator * f2.numerator, denominator * f2.denominator);
		return r;
	}
	public Fraction divide(Fraction f2){
		Fraction r = new Fraction (numerator*f2.denominator,denominator*f2.numerator);
		return r;
	}
	
	
	public static  String[] readFromFile(String file) throws IOException{
		String[] strokesArray = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line=reader.readLine();
		List<String> strokes = new ArrayList<String>();
		while (line != null) {
			strokes.add(line);
			line=reader.readLine();
		}
		reader.close();
		strokesArray = strokes.toArray(new String[strokes.size()]);}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return  strokesArray;
	}
	public static void printFile(String[] s){
		System.out.println("Вы ввели следующие выражения:");
		for(int i=0; i<s.length;i++){
			System.out.println(s[i]);
		}
	}
	public static void printChar(char[] c){
		for(int i=0; i<c.length;i++){
			System.out.print(c[i]);
		}
	}
	
	public static boolean checkWithRexExp(ArrayList<Character> c){
		String s ="";
		for(int i =0; i<c.size();i++)
			s+=c.get(i);
		Pattern p = Pattern.compile("[0-9][/][1-9][*+-:/][0-9][/][1-9]"); //любая цифра, потом /, потом 1-9, потом знак и тд
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	public static Fraction[] fileParse(){
		String[] s = null;
		try {
				s = readFromFile("test.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		//printFile(s);
		Fraction[] fractions = new Fraction[s.length];
		char[] stringMass = new char[s.length];
		ArrayList<ArrayList<Character>> stringMassChanged = new ArrayList<ArrayList<Character>>(); //измененный массив
		for (int i=0; i<s.length; i++){ //проходим по всем строкам файла
			ArrayList<Character> stringMassProm = new ArrayList<Character>();
			stringMass=s[i].toCharArray(); //переводим строки в чары
			for (int j=0; j<stringMass.length; j++){//проходим по каждому символу массива
				if(stringMass[j]!=' '){//убираем пробелы
					stringMassProm.add((Character)stringMass[j]); //получили строку без пробелов
				}
			}
			stringMassChanged.add(stringMassProm); //добавляем массив чаров без пробелов
		}
		for(int i=0; i<stringMassChanged.size();i++){
			if (checkWithRexExp(stringMassChanged.get(i))){
			Fraction f1 = new Fraction(Character.getNumericValue(stringMassChanged.get(i).get(0)) ,
					Character.getNumericValue(stringMassChanged.get(i).get(2)));
			Fraction f2 = new Fraction (Character.getNumericValue(stringMassChanged.get(i).get(4)),
					Character.getNumericValue(stringMassChanged.get(i).get(6)));
			char sign = stringMassChanged.get(i).get(3);
			switch(sign){
			case '+': fractions[i]=f1.plus(f2); break;
			case '-': fractions[i]=f1.minus(f2); break;
			case ':': fractions[i]=f1.divide(f2); break;
			case '/': fractions[i]=f1.divide(f2); break;
			case '*': fractions[i]=f1.multiply(f2); break;}
			}
			else {
				fractions[i] = null;
			}
		}
		writeInTxt(fractions, s);
		//printInFile(fractions,s);
		return fractions;
	}
public static void frintFracs(Fraction[] fr){
	for (int i =0; i<fr.length;i++){
		if(fr[i]==null){
			System.out.println("Ошибка в выражении!");
		}
		else{
		System.out.println(fr[i]);
		}
	}
	
}
public static void printInFile(Fraction[] fr, String[] s){
	System.out.println("Результат вычислений:");
	for (int i=0; i<s.length;i++){
		if(fr[i]==null)
		{
			System.out.println(s[i] + " = " + "Ошибка в выражении!");
		}
		else System.out.println(s[i] + " = " + fr[i]);
	}
	
	
}

public static void main(String []  args){
	Fraction[] fracs = fileParse();
	writeXML(fracs);
	}

public static void writeInTxt(Fraction[] fr, String[] s){
	File file = new File("Results_Ivanov.txt");
	try {
		if(!file.exists()){
			file.createNewFile();
		}
		PrintWriter out = new PrintWriter(file.getAbsoluteFile());
		try{
			out.print("Резульат вычислений:");
			for (int i=0; i<s.length;i++){
				if(fr[i]==null)
				{
					out.println(s[i] + " = " + "Ошибка в выражении!" + "\n");
				}
				else out.println(s[i] + " = " + fr[i] + "\n");
			}	
		} finally {
			out.close();
		}
	} catch(IOException e){
		throw new RuntimeException(e);
	}
	
}
public static void writeXML(Fraction[] fr){ //Запись с помощью технологии StAX
	try{
		XMLOutputFactory output = XMLOutputFactory.newInstance(); 
		XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter("Results_Ivanov.xml")); 
		// Открываем XML-документ и Пишем корневой элемент Results
		writer.writeStartDocument("Results"); 
		// Делаем цикл для дробей 
		for (int i = 0; i < fr.length; i++) { 
		// Записываем дробь 
		writer.writeStartElement("Fraction"); 
		// Заполняем все тэг для дроби 
		writer.writeStartElement("Answer"); 
		String s = fracToString(fr[i]);
		writer.writeCharacters(s); 
		writer.writeEndElement(); 
		}
		// Закрываем тэг Results 
		writer.writeEndElement(); 
		// Закрываем XML-документ 
		writer.writeEndDocument(); 
		writer.flush(); 
		} catch (XMLStreamException | IOException ex) { 
		ex.printStackTrace(); 
	}
	
	
}

}






