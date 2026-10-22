#!/bin/bash 
java -jar ./target/ep-pub-stomp.jar -Dfile.encoding=UTF-8 --xml.adapter=pub_stomp_adapter_v1.xml
