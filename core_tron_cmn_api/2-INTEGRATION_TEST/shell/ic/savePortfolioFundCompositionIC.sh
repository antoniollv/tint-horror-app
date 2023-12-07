#!/bin/bash
curl -v -u "APITRON:Mapfre2019" -d "cmpVal=1&lngVal=ES" -H "usrVal:TRON2000" http://trnic.desa.mapfre.net/nwt_cmn_api_be-web/newtron/api/common/pid/dfq/lodW/tblPrr --request POST
echo "Response from server"