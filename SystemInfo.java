import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Gets Information about various areas (basic, network, drive and operating system information) of a computer
 * that the computer is running on.
 * 
 * @author Matthew Carney (i7218006)
 * @version 1.0
 *
 */
public class SystemInfo {
		
		public void main(String[] args) {
			getBasicSystemInformation(1);
			getOSInformation();
			getDriveInformation(3);
			getNetworkInformation();
		}
		
		/**
		 * Retrives the basic system information for the computer that the JSysInfo is running on.
		 * 
		 * @param memoryFormat Specifies what format the memory values returned by the method.
		 * 		  					"1" is for the method to return the values in Kilobytes
		 * 		  					"2" is for the method to return the values in Megabytes
		 * 		  					"3" if for the method to return the values in Gigabytes
		 * @return userName			Returns the username of the computers current user
		 * @return userHomeDir		Returns the home directory of the computers current user
		 * @return currentDir		Returns the current directory of the program
		 * @return processorCores	Returns the number of cores present in the computers processor
		 * @return freeRam			Returns the current amount of free Random Access Memory (RAM) based on RAM avaliable to JVM
		 * @return maxRam			Returns the max amount Random Access Memory (RAM) based on RAM avaliable to JVM
		 * @return totJvmRam		Returns the total amount of Random Access Memory (RAM) that is allocated to the JVM
		 */
		public String getBasicSystemInformation(int memoryFormat) {
			int processorCores;
			String userName, userHomeDir, currentDir, freeRam = null, maxRam = null, totJvmRam = null;
			
			processorCores = Runtime.getRuntime().availableProcessors();
			userName = System.getProperty("user.name");
			userHomeDir = System.getProperty("user.home");
			currentDir = System.getProperty("user.dir");
			
			switch (memoryFormat) { // freeRamValue, maxRamValue and totJvmRam individually declared in each case due to KB and MB formats needing to be long and GB format needing to be short (in order to show decimal places and be more precise)
				case 1: { // For when the user wants the data to be in Kilobytes
					long freeRamValue = getValueKilobytes(Runtime.getRuntime().freeMemory());
					long maxRamValue = getValueKilobytes(Runtime.getRuntime().maxMemory());
					long totJvmRamValue = getValueKilobytes(Runtime.getRuntime().totalMemory());
					freeRam = Long.toString(freeRamValue) + "KB";
					maxRam = Long.toString(maxRamValue) + "KB";
					totJvmRam = Long.toString(totJvmRamValue) + "KB";
					break;
				}
				case 2: { // For when the user wants the data to be in Megabytes
					long freeRamValue = getValueMegabytes(Runtime.getRuntime().freeMemory());
					long maxRamValue = getValueMegabytes(Runtime.getRuntime().maxMemory());
					long totJvmRamValue = getValueMegabytes(Runtime.getRuntime().totalMemory());
					freeRam = Long.toString(freeRamValue) + "MB";
					maxRam = Long.toString(maxRamValue) + "MB";
					totJvmRam = Long.toString(totJvmRamValue) + "MB";
					break;
					}
				case 3: { // For when the user wants the data to be in Gigabytes
					double freeRamValue = getValueGigabytes(Runtime.getRuntime().freeMemory());
					double maxRamValue = getValueGigabytes(Runtime.getRuntime().maxMemory());
					double totJvmRamValue = getValueGigabytes(Runtime.getRuntime().totalMemory());
					freeRam = String.valueOf(freeRamValue) + "GB";
					maxRam = String.valueOf(maxRamValue) + "GB";
					totJvmRam = String.valueOf(totJvmRamValue) + "GB";
					break;
				}
			}
			return "Basic System Information\r\n" + "########################\r\n" + "Username: " + userName + "\r\n" + "Home Directory: " + userHomeDir + "\r\n" + "Current Directory: " + currentDir +"\r\n" + "Number of Processor Cores: " + processorCores +"\r\n" + "Max Physical Memory: " + maxRam + "\r\n" + "Free Physical Memory: " + freeRam +"\r\n" + "Available JVM Physical Memory: " + totJvmRam + "\r\n" + "\r\n[Note: Physical memory values based on memory  avaliable to the JVM so may not represent actu-al physical memory available to the system.]" + "\r\n\r\n";
		}
		
		/**
		 * Retrives Information about the Operating System that is installed on the computer that the program is running on.
		 * 
		 * @return osName		Returns the name of the Operating System running on the computer
		 * @return osVersion	Returns the current version of the Operating System running on the computer
		 * @return osArch		Returns the architecture that the computers Operating System is based on
		 */
		public String getOSInformation() {
			String osName, osVersion, osArch;
			osName = System.getProperty("os.name");
			osVersion = System.getProperty("os.version");
			osArch = System.getProperty("os.arch");
			return "Operating System Information\r\n" +"########################\r\n" + "OS Name: " + osName + "\r\n" + "OS Version: " + osVersion + "\r\n" + "OS Architecture: " + osArch + "\r\n\r\n";
		}
		
		/**
		 * Gets information about the current drives that are installed and are connected to the computer when the program is run.
		 * This includes Harddrives, Disk Drives, CDs present in disk drives, Solid State Harddrives, Removable Harddrives, Network Drives and Memory Sticks.
		 * 
		 * @param memoryFormat Specifies what format the memory values returned by the method.
		 * 		  					"1" is for the method to return the values in Kilobytes
		 * 		  					"2" is for the method to return the values in Megabytes
		 * 		  					"3" if for the method to return the values in Gigabytes
		 * @return completeReturnString		is string that is created from an array 
		 * 									that contains all of the drive information including drive root, free space, usuable space and max space.
		 * 									The array has been concatinated into one string so it can be easily returned to the GUI with one return statement.
		 */
		public String getDriveInformation(int memoryFormat) {
			int count = 0;
			String completeReturnString = "Drive Information\r\n########################\r\n"; // Add driver header to start of the return string
			File[] drivePaths = File.listRoots();
			String[] returnString = new String[drivePaths.length];
			switch (memoryFormat) {
				case 1: { // For when the user wants the data to be in Kilobytes
					for (File drivePath : drivePaths) { // Get data from file array and put it into a String array along with text which can be returned to the gui
						returnString[count] = "File System Root: " + drivePath.getAbsolutePath() + "\r\n" + "Total Space: " + getValueKilobytes(drivePath.getTotalSpace()) + "KB\r\n" + "Free Space: " + getValueKilobytes(drivePath.getFreeSpace()) + "KB\r\n" + "Usable Space: " + getValueKilobytes(drivePath.getUsableSpace()) + "KB\r\n\r\n";
						count++;
					}
					break;
				}
				case 2: { // For when the user wants the data to be in Megabytes
					for (File drivePath: drivePaths) { 
						returnString[count] = "File System Root: " + drivePath.getAbsolutePath() + "\r\n" + "Total Space: " + getValueMegabytes(drivePath.getTotalSpace()) + "MB\r\n" + "Free Space: " + getValueMegabytes(drivePath.getFreeSpace()) + "MB\r\n" + "Usable Space: " + getValueMegabytes(drivePath.getUsableSpace()) + "MB\r\n\r\n";
						count++;
					}
					break;
				}
				case 3: { // For when the user wants the data to be in Gigabytes
					for (File drivePath: drivePaths) { 
						returnString[count] = "File System Root: " + drivePath.getAbsolutePath() + "\r\n" + "Total Space: " + getValueGigabytes(drivePath.getTotalSpace()) + "GB\r\n" + "Free Space: " + getValueGigabytes(drivePath.getFreeSpace()) + "GB\r\n" + "Usable Space: " + getValueGigabytes(drivePath.getUsableSpace()) + "GB\r\n\r\n";
						count++;
					}
					break;
				}
			}
			for (String string : returnString) { 
				completeReturnString = completeReturnString + string; // Concatinate array to one string to return to gui
			}
			return completeReturnString;
		}
		
		/**
		 * Gets a list on network interfaces/connections that are active on the computer when the program is run.
		 * It only lists interfaces that have an Internet Protocol (IP) Address	associated with them.
		 * 
		 * @return returnString			Returns a concatinated array that contains all the information about interfaces present
		 * 								on the computer than have an IP address associated with them.
		 * @exception SocketException	An excpetion thrown when handling network interfaces. Is caught by my try catch statement.
		 */
		public String getNetworkInformation() {
			String tempNameHolder = null, returnString = "Network Information\r\n########################\r\n"; // To use when finding duplicates in arraylist
			boolean matchedStringSW = false;
			ArrayList<String> returnStrings = new ArrayList<String>(); 
			returnStrings.add("");
			try {
				Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces(); // Get list of network interfaces and put in an array
				for (NetworkInterface networkInterfaces :  Collections.list(networks)) {
					Enumeration<InetAddress> networkAddresses = networkInterfaces.getInetAddresses(); // Get list of IPs associated with a network interface
					for (InetAddress networkAddress : Collections.list(networkAddresses)) { 
						tempNameHolder = networkInterfaces.getName();
						for (String string : returnStrings) { // Loop through strings that are going to be returned (to prevent duplicate Interface names with multiple associated addresses)
							if (tempNameHolder.equals(string)) {
								returnStrings.add("Associated IP: " + networkAddress + "\r\n\r\n");// network name already in the list so don't store the name just store the associated address
								matchedStringSW = true;
								break; // Search has been completed so break out of the loop
							}
						}
						if (matchedStringSW == true) { 
							matchedStringSW = false;
							break;
						}
						returnStrings.add("Display Name: ");// If the string isn't found in the arraylist, add it to the array list 
						returnStrings.add(networkInterfaces.getDisplayName());
						returnStrings.add("\r\n"); 
						returnStrings.add("Name: ");
						returnStrings.add(networkInterfaces.getName());
						returnStrings.add("\r\n");
						returnStrings.add("Associated IP: " + networkAddress + "\n");
					}
				}
			} 
			catch (SocketException exception) {
				returnStrings.add("ERROR: Unable to retrive interface information due to [SOCKET ERROR]");
			}
			finally { }
			for (String string : returnStrings) {
				returnString = returnString + string;
			}
			return returnString;
		}
		
		/**
		 * Returns a string that contains the name of the current user on the computer that the
		 * program is running on
		 * 
		 * @return userName		A string that contains the username of the current user.
		 */
		static String getUserName() {
			String userName;
			userName = System.getProperty("user.name");
			return userName;
		}
		
		/**
		 * A method that gets a value (in bytes) and convertes it into Kilobytes.
		 * 
		 * @param value		A value, in bytes, to be converted into Kilobytes.
		 * @return convertedValue	the converted value in kilobytes that is returned in long.
		 */
		static long getValueKilobytes(long value) {
			long convertedValue;
			convertedValue = value / 1024;
			return convertedValue;
		}
		
		/**
		 * A method that gets a value (in bytes) and convertes it into Megabytes.
		 * 
		 * @param value		A value, in bytes, to be converted into Megabytes.
		 * @return convertedValue	the converted value in Megabytes that is returned in long.
		 */
		static long getValueMegabytes(long value) {
			long convertedValue;
			convertedValue = value / 1048576;
			return convertedValue;
		}
		
		/**
		 * A method that gets a value (in bytes) and convertes it into Gigabytes with two decimal places.
		 * 
		 * @param value		A value, in bytes, to be converted into Gigabytes.
		 * @return convertedValue	The converted value in Gigabytes with 2 decimal places that is returned as a double.
		 */
		static double getValueGigabytes(long value) {
			double convertedValue, valueDouble; 
			String formattedConvertedValue; // Stores the formated double with less decimal places [has to be a string in order to DecimalFormat]
			DecimalFormat doubleFormat = new DecimalFormat("#.00"); // Creates a number format to limit the amount of decimal places to two
			valueDouble = (double) value; 
			convertedValue = valueDouble / 1073741824;
			formattedConvertedValue = doubleFormat.format(convertedValue); 
			convertedValue = Double.parseDouble(formattedConvertedValue); 
			return convertedValue;
		}

}
