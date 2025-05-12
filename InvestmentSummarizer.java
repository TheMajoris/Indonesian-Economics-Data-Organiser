import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class InvestmentSummarizer {
    public static void main(String[] args) {
        String inputCsvFile = "numbers.csv";
        String outputCsvFile = "result.csv";
        
        // Format for decimal places
        DecimalFormat df = new DecimalFormat("#.####");
        df.setGroupingUsed(false);
        
        // Fixed order for main sectors
        List<String> mainSectorOrder = Arrays.asList(
            "Sektor Primer", 
            "Sektor Sekunder", 
            "Sektor Tersier"
        );
        
        // Fixed order for sub-sectors (nama_sektor)
        // This list contains all possible nama_sektor values in the desired order
        List<String> subSectorOrder = Arrays.asList(
            "Pertambangan",
            "Tanaman Pangan, Perkebunan, dan Peternakan",
            "Kehutanan",
            "Perikanan",
            "Industri Makanan",
            "Industri Tekstil",
            "Industri Kayu",
            "Industri Kertas dan Percetakan",
            "Industri Kimia Dan Farmasi",
            "Industri Karet dan Plastik",
            "Industri Mineral Non Logam",
            "Industri Logam Dasar, Barang Logam, Bukan Mesin dan Peralatannya",
            "Industri Mesin, Elektronik, Instrumen Kedokteran, Peralatan Listrik, Presisi, Optik dan Jam",
            "Industri Kendaraan Bermotor dan Alat Transportasi Lain",
            "Industri Lainnya",
            "Industri Barang dari Kulit dan Alas Kaki",
            "Listrik, Gas dan Air",
            "Konstruksi",
            "Perdagangan dan Reparasi",
            "Hotel dan Restoran",
            "Transportasi, Gudang dan Telekomunikasi",
            "Perumahan, Kawasan Industri dan Perkantoran",
            "Jasa Lainnya"
        );
        
        // Maps to store totals for each sector
        Map<String, Double> mainSectorTotals = new HashMap<>();
        Map<String, Double> subSectorTotals = new HashMap<>();
        
        // Initialize all sectors with zero
        for (String sector : mainSectorOrder) {
            mainSectorTotals.put(sector, 0.0);
        }
        
        for (String sector : subSectorOrder) {
            subSectorTotals.put(sector, 0.0);
        }
        
        try {
            // Calculate sums
            try (BufferedReader br = new BufferedReader(new FileReader(inputCsvFile))) {
                String header = br.readLine(); // Read header row
                String[] headers = parseCSVLine(header);
                
                // Find column indices
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
                
                // Process each data row
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    
                    String[] data = parseCSVLine(line);
                    
                    if (data.length <= Math.max(Math.max(sektorUtamaIndex, namaSektorIndex), investasiIndex)) {
                        continue;
                    }
                    
                    String sektorUtama = data[sektorUtamaIndex].trim();
                    String namaSektor = data[namaSektorIndex].trim();
                    
                    if (sektorUtama.isEmpty() || namaSektor.isEmpty()) {
                        continue;
                    }
                    
                    // Parse investment value
                    double investmentValue = 0.0;
                    try {
                        if (!data[investasiIndex].trim().isEmpty()) {
                            investmentValue = Double.parseDouble(data[investasiIndex].trim());
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    
                    // Add to main sector total if it's in our list
                    if (mainSectorTotals.containsKey(sektorUtama)) {
                        mainSectorTotals.put(sektorUtama, mainSectorTotals.get(sektorUtama) + investmentValue);
                    }
                    
                    // Add to sub-sector total if it's in our list
                    if (subSectorTotals.containsKey(namaSektor)) {
                        subSectorTotals.put(namaSektor, subSectorTotals.get(namaSektor) + investmentValue);
                    }
                }
            }
            
            // Write results to output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvFile))) {
                // Create header and data lines
                List<String> headerFields = new ArrayList<>();
                List<String> valueFields = new ArrayList<>();
                
                // Add main sectors
                headerFields.add("sektor primer");
                headerFields.add("sektor sekunder");
                headerFields.add("sektor tersier");
                
                for (String sector : mainSectorOrder) {
                    valueFields.add(df.format(mainSectorTotals.get(sector)));
                }
                
                // Add sub-sectors in fixed order
                for (String sector : subSectorOrder) {
                    if (subSectorTotals.get(sector) > 0.0) { // Only include sectors with values
                        headerFields.add(sector);
                        valueFields.add(df.format(subSectorTotals.get(sector)));
                    }
                }
                
                // Write to file with proper CSV formatting
                writeCSVLine(writer, headerFields);
                writer.newLine();
                writeCSVLine(writer, valueFields);
                writer.newLine();
                
                System.out.println("Results written to " + outputCsvFile);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Write a CSV line with proper quoting for fields with commas
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
    
    // CSV parser that handles quoted fields with commas
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
                // If we're in quotes and the next char is also a quote, it's an escaped quote
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
                sb.setLength(0); // Reset StringBuilder
            } else {
                // Regular character
                sb.append(c);
            }
        }
        
        // Add the last field
        tokens.add(sb.toString());
        
        return tokens.toArray(new String[0]);
    }
}
