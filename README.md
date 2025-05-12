# Indonesian Economic Data Organizer for Indonesian Ministry of Investment Datasets

## Overview
Investment Summarizer is a Java application that processes CSV investment data and calculates sector-based investment sums. It analyzes the "investasi_rp_juta" (investment in million rupiah) column, grouping values by sector categories.

## Features
- Processes investment data from CSV files
- Calculates investment sums for main sectors (Sektor Primer, Sektor Sekunder, Sektor Tersier)
- Separately calculates sums for each unique sub-sector (nama_sektor)
- Formats numeric values to 4 decimal places
- Handles CSV fields with commas in quoted text
- Exports results to a new CSV file

## Requirements
- **Java Runtime Environment (JRE)**: Version 21 or newer
- Compatible with Windows, macOS, and Linux

## Installation
1. Download the InvestmentSummarizer.jar file
2. Ensure Java 21+ is installed on your system
3. Place your investment data CSV file in the same directory as the JAR file

## Usage
### Running the Application
- **Windows**: Double-click the JAR file or run via command line:
  ```
  java -jar InvestmentSummarizer.jar
  ```
- **macOS/Linux**: Run via terminal:
  ```
  java -jar InvestmentSummarizer.jar
  ```

### Input Format
The program expects a CSV file named `numbers.csv` with the following required columns:
- `sektor_utama` - Main sector category
- `nama_sektor` - Sub-sector name
- `investasi_rp_juta` - Investment amount in million rupiah

### Output Format
The program generates `result.csv` with:
- **Header row**: `sektor primer, sektor sekunder, sektor tersier, [sub-sector1], [sub-sector2], ...`
- **Values row**: Calculated investment sums corresponding to each header

## Troubleshooting

### Java Version Error
If you see this error:
```
java.lang.UnsupportedClassVersionError: InvestmentSummarizer has been compiled by a more recent version of the Java Runtime (class file version 65.0), this version of the Java Runtime only recognizes class file versions up to 61.0
```

**Solution**: 
1. Update your Java installation to version 21 or newer
2. Download from: https://www.oracle.com/java/technologies/downloads/

### CSV Parsing Issues
- Ensure your CSV file uses standard formatting
- The program handles fields with commas inside quotes
- Complex field names like "Perumahan, Kawasan Industri dan Perkantoran" are properly processed
