#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

#--------------------
# Push Applications
# jdbc-sql-console-app
#cf push gemfire-gideon-console -f deployments/cloud/cloudFoundry/apps/gemfire-gideon-console/gemfire-gideon-console.yaml -o gemfire/gemfire-management-console:1.2.1


cf push gemfire-console -f deployments/cloud/cloudFoundry/apps/gemfire-gideon-console/gemfire-console.yaml -p /Users/devtools/repositories/IMDG/gemfire/gideon-console/vmware-gemfire-management-console-1.2.1.jar

