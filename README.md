# WearableDataServer
<p><a href="https://travis-ci.org/OhtuWearable/WearableDataServer"><img src="https://travis-ci.org/OhtuWearable/WearableDataServer.svg?branch=master" alt="Build Status" /></a></p>

WearableDataServer is an IoT-hub -implementation for Android Wearables to control queries for Android sensors data via RESTful API feeds (GET). WearableDataServer uses <a href="https://github.com/NanoHttpd/nanohttpd">NanoHttpd</a> for HTTP-server functionalities.

WearableDataServer includes GUI where user may select sensor(s) to be listened from a list of sensors that are available on a device. REST-server may then be asked for JSON-formatted sensor data. Different sensor feeds are identified by sensor's id number, e.g. /feeds/1 returns Android's accelerometer data.


Copyright 2015 University of Helsinki
Licensed under the Apache License, Version 2.0
