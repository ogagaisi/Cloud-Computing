/**
 * 
 */
package simproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisioner;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import com.sun.corba.se.pept.broker.Broker;

import sun.font.CreatedFontTracker;

/**
 * @author ogaga isiavwe
 * @param <Storge>
 *
 */
public class cloudSim2<Storge> {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//1.0: Initialize the CloudSim package. It should be called before creating any entities.

		Boolean traceFlag = false;
		Calendar cal = Calendar.getInstance(); // gets the local time
		
		int numUser = 1;
		int pesNumber = 1;
		int bandwidth = 1000,
				mips = 1000,
				ram = 2000,
				vCPU = 1;
		List<Cloudlet> cletList = new ArrayList<Cloudlet>();
		List<Vm> vmList = new ArrayList<Vm>();
		String  vmm = "XEN"; //Virtual machine management 
		long cloudletFileSize = 300,
			cloudletLength = 4000,
			cloudletOutputSize = 400;
		long disksize = 20000;
		 
		
		CloudSim.init(numUser, cal, traceFlag);
		Datacenter dc = createDataCenter();
		//3.0: Create Broker
		DatacenterBroker dcb = null;
		try{
			dcb = new DatacenterBroker("dcBroker1");
		}
		catch (Exception e){
			e.printStackTrace();			
		}
		//4.0: Create Cloudlets:Defines the workload
		//cloudletId, 

		 
		UtilizationModelFull fullUtilize = new UtilizationModelFull();
		for(int i = 0; i <40; i++){
			Cloudlet c = new Cloudlet(i, cloudletLength, pesNumber, cloudletFileSize, cloudletOutputSize, fullUtilize,fullUtilize,fullUtilize);
			c.setUserId(dcb.getId());
			cletList.add(c);
			
		}
		//5.0: Create VMs:Define the procedure for Task scheduling algorithm
		
		for (int id = 0; id <40; id++){
			Vm vm = new Vm(id, dcb.getId(), mips, vCPU, ram, bandwidth, disksize, vmm, new CloudletSchedulerTimeShared());
			vmList.add(vm);
			
		}
		dcb.submitCloudletList(cletList);
		dcb.submitVmList(vmList);
		//6.0: Starts the simulation: Automated process, handled through descreted event simulation engine
		CloudSim.startSimulation();	
		List<Cloudlet> finalResults = dcb.getCloudletReceivedList();
		CloudSim.stopSimulation();
		//7.0: Print results when simulation is over as Outputs
		
		int cloudletNo = 0;
		for(Cloudlet c : finalResults){
			Log.printLine("Result of cloudlet No " + cloudletNo);
			Log.printLine("************************************");
			Log.printLine("id:" +c.getCloudletId() + ", VM:" + c.getVmId() + ", status:" + c.getStatus() + ", execution time:"+ c.getActualCPUTime()+", start:" + c.getExecStartTime()+ ", end:" + c.getFinishTime());
			Log.printLine("************************************");
			cloudletNo++;
		}

	}
	
	//2.0: Create Datacenter: Datacenter k-- Datacentercharacteristics K-- HostList K-- Processing element List, Also Defines policy for VM allocation and scheduling
	private static Datacenter createDataCenter(){
		Datacenter dc = null;
		double costPerBw = 0.1,
				costPerMem =1.0,
				costPerSec = 3.0,
				costPerStorage = 0.05,
				timeZone = 5.0;
				
		Host host = null;	
		int ram = 8000;
		LinkedList<Storage> SANstorage = new LinkedList<Storage>();
		List<Host> hostList = new ArrayList<Host>();
		List<Pe> peList = new ArrayList<Pe>(); //Processing element (CPU) 
		long bw = 8000;//Bandwidth
		long storage = 100000;
		PeProvisionerSimple peProv =  new PeProvisionerSimple(1000);
		Pe core = null;
		String architecture = "x86", 
				os = "Linux", 
				vmm = "XEN"; 
		
		
		
		// Creates 4 cores and adds it to peList
		for (int i = 0; i < 4; i++){
			core = new Pe(i, peProv);
			peList.add(core);
		}
		
		for (int i = 0; i<4; i++){
			host = new Host(i, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
			hostList.add(host);
		}

		
		DatacenterCharacteristics dcCharacteristics = new DatacenterCharacteristics(architecture, os, vmm, hostList, timeZone, costPerSec, costPerMem, costPerStorage, costPerBw);
		
		try{
			dc = new Datacenter("Datacenter1", dcCharacteristics, new VmAllocationPolicySimple(hostList), SANstorage, 1);
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
			
		return dc;
	}

}
