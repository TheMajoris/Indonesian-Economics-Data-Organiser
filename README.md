# Investment Summarizer

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
The program expects a CSV file with the following required columns:
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

## Contact
For support or feature requests, please contact the development team.

Citations:
[1] https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/55132172/e1a570cc-9907-458b-a496-e9ce1324b505/numbers.csv
[2] https://pplx-res.cloudinary.com/image/private/user_uploads/55132172/2b6bad07-c294-4891-9755-74b8f22660c6/image.jpg
[3] https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/55132172/e1a570cc-9907-458b-a496-e9ce1324b505/numbers.csv
[4] https://pplx-res.cloudinary.com/image/private/user_uploads/55132172/2b6bad07-c294-4891-9755-74b8f22660c6/image.jpg

---
Answer from Perplexity: pplx.ai/share
