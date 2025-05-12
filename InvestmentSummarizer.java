import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class InvestmentSummarizer {
    public static void main(String[] args) {
        String inputCsvFile = "numbers.csv";
        String outputCsvFile = "result.csv";
        
        DecimalFormat df = new DecimalFormat("#.####");
        df.setGroupingUsed(false);
        
        Map<String, Double> sektorUtamaTotals = new LinkedHashMap<>();
        sektorUtamaTotals.put("Sektor Primer", 0.0);
        sektorUtamaTotals.put("Sektor Sekunder", 0.0);
        sektorUtamaTotals.put("Sektor Tersier", 0.0);
        
        Map<String, Double> namaSektorTotals = new LinkedHashMap<>();
        
        try {
            Map<String, Boolean> namaSektorHasValues = new HashMap<>();
            
            try (BufferedReader br = new BufferedReader(new FileReader(inputCsvFile))) {
                String header = br.readLine();
                String[] headers = parseCSVLine(header);
                
                int namaSektorIndex = -1;
                int investasiIndex = -1;
                
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].trim().equalsIgnoreCase("nama_sektor")) {
                        namaSektorIndex = i;
                    } else if (headers[i].trim().equalsIgnoreCase("investasi_rp_juta")) {
                        investasiIndex = i;
                    }
                }
                
                if (namaSektorIndex == -1 || investasiIndex == -1) {
                    System.out.println("Required columns not found!");
                    return;
                }
                
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    
                    String[] data = parseCSVLine(line);
                    
                    if (data.length <= Math.max(namaSektorIndex, investasiIndex)) {
                        continue;
                    }
                    
                    String namaSektor = data[namaSektorIndex].trim();
                    
                    if (!namaSektor.isEmpty()) {
                        if (!namaSektorHasValues.containsKey(namaSektor)) {
                            namaSektorHasValues.put(namaSektor, false);
                            namaSektorTotals.put(namaSektor, 0.0);
                        }
                        
                        try {
                            String investasiStr = data[investasiIndex].trim();
                            if (!investasiStr.isEmpty()) {
                                double value = Double.parseDouble(investasiStr);
                                namaSektorHasValues.put(namaSektor, true);
                            }
                        } catch (NumberFormatException e) {

                        }
                    }
                }
            }
            
            List<String> toRemove = new ArrayList<>();
            for (Map.Entry<String, Boolean> entry : namaSektorHasValues.entrySet()) {
                if (!entry.getValue()) {
                    toRemove.add(entry.getKey());
                }
            }
            
            for (String key : toRemove) {
                namaSektorTotals.remove(key);
            }
            
            try (BufferedReader br = new BufferedReader(new FileReader(inputCsvFile))) {
                String header = br.readLine();
                String[] headers = parseCSVLine(header);
                
                int sektorUtamaIndex = -1;
                int namaSektorIndex = -1;
                int investasiIndex = -1;
                
                for (int i = 0; i < headers.length; i++) {
                    String col = headers[i].trim();
                    if (col.equalsIgnoreCase("sektor_utama")) {
                        sektorUtamaIndex = i;
                    } else if (col.equalsIgnoreCase("nama_sektor")) {
                        namaSektorIndex = i;
                    } else if (col.equalsIgnoreCase("investasi_rp_juta")) {
                        investasiIndex = i;
                    }
                }
                
                if (sektorUtamaIndex == -1 || namaSektorIndex == -1 || investasiIndex == -1) {
                    System.out.println("Required columns not found!");
                    return;
                }
                
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    
                    String[] data = parseCSVLine(line);
                    
                    if (data.length <= Math.max(Math.max(sektorUtamaIndex, namaSektorIndex), investasiIndex)) {
                        continue;
                    }
                    
                    String sektorUtama = data[sektorUtamaIndex].trim();
                    String namaSektor = data[namaSektorIndex].trim();
                    
                    if (!sektorUtamaTotals.containsKey(sektorUtama) || namaSektor.isEmpty()) {
                        continue;
                    }
                    
                    if (!namaSektorTotals.containsKey(namaSektor)) {
                        continue;
                    }
                    
                    double investmentValue = 0.0;
                    try {
                        if (!data[investasiIndex].trim().isEmpty()) {
                            investmentValue = Double.parseDouble(data[investasiIndex].trim());
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    
                    sektorUtamaTotals.put(sektorUtama, sektorUtamaTotals.get(sektorUtama) + investmentValue);
                    namaSektorTotals.put(namaSektor, namaSektorTotals.get(namaSektor) + investmentValue);
                }
            }
            
            List<String> outputHeaders = new ArrayList<>();
            List<String> outputValues = new ArrayList<>();
            
            outputHeaders.add("sektor primer");
            outputHeaders.add("sektor sekunder");
            outputHeaders.add("sektor tersier");
            
            outputValues.add(df.format(sektorUtamaTotals.get("Sektor Primer")));
            outputValues.add(df.format(sektorUtamaTotals.get("Sektor Sekunder")));
            outputValues.add(df.format(sektorUtamaTotals.get("Sektor Tersier")));
            
            for (Map.Entry<String, Double> entry : namaSektorTotals.entrySet()) {
                outputHeaders.add(entry.getKey());
                outputValues.add(df.format(entry.getValue()));
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvFile))) {

                writeCSVLine(writer, outputHeaders);
                writer.newLine();

                writeCSVLine(writer, outputValues);
                writer.newLine();
            }
            
            // Print totals for verification
            System.out.println("Main sector totals:");
            System.out.println("Sektor Primer: " + df.format(sektorUtamaTotals.get("Sektor Primer")));
            System.out.println("Sektor Sekunder: " + df.format(sektorUtamaTotals.get("Sektor Sekunder")));
            System.out.println("Sektor Tersier: " + df.format(sektorUtamaTotals.get("Sektor Tersier")));
            
            System.out.println("\nResults written to " + outputCsvFile);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void writeCSVLine(BufferedWriter writer, List<String> fields) throws IOException {
        StringBuilder line = new StringBuilder();
        
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            
            // Add quotes around fields containing commas
            if (field.contains(",")) {
                line.append("\"").append(field).append("\"");
            } else {
                line.append(field);
            }
            
            // Add comma except for last field
            if (i < fields.size() - 1) {
                line.append(",");
            }
        }
        
        writer.write(line.toString());
    }
    
    private static String[] parseCSVLine(String line) {
        if (line == null || line.isEmpty()) {
            return new String[0];
        }
        
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++; // Skip the next quote
                } else {
                    // Toggle in-quotes status
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                // End of field
                tokens.add(sb.toString());
                sb.setLength(0); 
            } else {
                // Regular character
                sb.append(c);
            }
        }
        
        tokens.add(sb.toString());
        
        return tokens.toArray(new String[0]);
    }
}