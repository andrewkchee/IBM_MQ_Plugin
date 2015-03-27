# IBM_MQ_Plugin
==========================================================
- - -
The IBM MQ Plugin monitors a particular queue on an IBM MQ server and reports the current queue depth, queue full percentage, output input count, and open output count.

This plugin is not complete and only includes the agent code and Eclipse project and is meant as an example of how to connect to IBM MQ and get some information from
it.

##Prerequisites

*    A New Relic account. If you are not already a New Relic user, you can signup for a free account at http://newrelic.com
*    A New Relic valid license key. You can obtain the license key fromn your New Relic account under 'Account Settings'
*    Obtain the New Relic Ping plugin
*    A configured Java Runtime (JRE) environment Version 1.7+
*    Network access to New Relic

##Additional Plugin Details:

This plugin reads the plugin.json file to get connection information to the MQ Server
The configuration file requires the following information:
* Name for plugin instance
* URL to MQ Server
* port on MQ Server
* Channel on MQ Server
* Username
* Password
* Queue manager name
* Queue name

The metrics reported show up as:
* /Component/IBMMQ/QueueDepth
* /Component/IBMMQ/OpenInputCount
* /Component/IBMMQ/OpenOutoutCount
* /Component/IBMMQ/PercentageFull
