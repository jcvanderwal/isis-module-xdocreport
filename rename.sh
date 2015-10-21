#!/bin/bash
module_name=Xdocreport
module_namel=xdocreport

find . -type f -not -iwholename "*.git*" | while read line; do sed -i 's/$module_name/$module_name/g' $line; done
find . -type f -not -iwholename "*.git*" | while read line; do sed -i 's/$module_namel/$module_namel/g' $line; done

find . -type f -name "$module_name*" | while read line; do echo $line ${line/$module_name/$module_name}; done
find . -type d -name "$module_namel*" | while read line; do echo $line ${line/$module_namel/$module_namel}; done
