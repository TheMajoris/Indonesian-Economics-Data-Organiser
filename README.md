Field,Description
Program Name,InvestmentSummarizer.jar
Version,1.0
Purpose,"Calculates and exports the sum of investments (investasi_rp_juta) by sector categories"
Input File,CSV file containing investment data with columns including sektor_utama and nama_sektor
Output File,result.csv containing sums of investments grouped by main sectors and sub-sectors
Required JRE,Java 21 or newer (supports class file version 65.0)
Usage,"Double-click the JAR file or run 'java -jar InvestmentSummarizer.jar' from command line"
Input Format,"CSV with header row and required columns: sektor_utama, nama_sektor, investasi_rp_juta"
Output Format,"Header row: sektor primer, sektor sekunder, sektor tersier, [nama_sektor1], [nama_sektor2]..."
,"Values row: [sum1], [sum2], [sum3], [subsector_sum1], [subsector_sum2]..."
Notes,"Values are formatted to 4 decimal places; sectors with no values are excluded"
Error,"If you see 'UnsupportedClassVersionError', update your Java version - program requires Java 21+"
Download JRE,"https://www.oracle.com/java/technologies/downloads/ or https://www.java.com/en/download/manual.jsp"
Compatibility Issue,"Error message 'class file version 65.0' means you need to update your Java Runtime Environment"
