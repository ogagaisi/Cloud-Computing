# Cloud-Computing
<h2>Cloud computing projects &amp; simulations.</h2>
<ol>
  <li>
      <b>SimpleCloudSim.java:</b><br>
    A simulation of a cloud service using the CloudSim 3.0.0 frame work and common-maths. It has 1 customer, broker, datacenter and cloudlet. The cloudlet has a virtual machine with 1500 mips (Million instructions per second), 10GB image size(10,000 MB), ram size of 600MB, and 1 cpu<br><br>
    common-maths: http://commons.apache.org/proper/commons-math/download_math.cgi<br>
    cloudSim: https://github.com/Cloudslab/cloudsim
  </li>
  <li>
    <b>cloudSim2.java</b><br>
    A simulation of a cloud service using CloudSim 3.0.0<br>
    <b>Features:</b>
    <ol>
      <li>One Datacenter: Four Hosts, Four cores with 1000 mips in each core, 8 GB RAM, 100 GB storage i.e. 100000 MB, 1 mbps i.e. 8000 Kbits/s           network bandwidth measured as Kbits/s
      </li>
      <li>One DataCenterBroker</li>
      <li>CloudLets: 40 Cloudlets/tasks/workload, 40000 length of instructions, 300 kb input filesize, 400 kb output filesize, 1 core cpu
        utilization model to full</li>
      <li>Virtual Machines: 40 Virtual machines, 20 GB Storage disk, 2 GB RAM, 1 vCPU with 1000 mips CPU speed, 1000 kbits/s Bandwidth, Timeshared scheduler for cloudlets execution</li>
    </ol>
  </li>
 </ol>
