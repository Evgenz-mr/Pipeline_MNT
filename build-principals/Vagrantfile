NODE_COUNT=2

Vagrant.configure("2") do |config|
 config.vm.define "master" do |subconfig|
   subconfig.vm.box = "sbeliakou/centos-7.4-x86_64-minimal"
    subconfig.vm.hostname = "master"
     subconfig.vm.network :private_network, ip: "192.168.56.101"
      subconfig.vm.provision :shell, :path => "./vagrant_master.sh"
       subconfig.vm.synced_folder "./opt/jenkins", "/opt/jenkins"
             config.vm.provider :virtualbox do |v|
    v.customize ["modifyvm", :id, "--memory", 2048]
v.name="master"
   
 end
end



(1..NODE_COUNT).each do |i|

   config.vm.define "slave#{i}" do |subconfig|
      subconfig.vm.box = "sbeliakou/centos-7.4-x86_64-minimal"
      subconfig.vm.hostname = "slave#{i}"
      subconfig.vm.network :private_network, ip: "192.168.56.10#{i + 1}"
      subconfig.vm.provision :shell, :path => "./vagrant_slave.sh"
     subconfig.vm.synced_folder "./opt#{i}/jenkins", "/opt/jenkins"
config.vm.provider :virtualbox do |v|

   v.customize ["modifyvm", :id, "--memory", 1048]
v.name="slave#{i}"
 
    end

   end
    end

end

