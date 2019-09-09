 //json数据转excel
function JSONToExcelConvertor(JSONData, FileName) {  
    //先转化json  
    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;  
    var excel = '<table>';   
    var row = "<tr>";           
    //设置表头  
    var keys = Object.keys(JSONData[0]);  
    keys.forEach(function (item) {
        row += "<td>" + item + '</td>';
    });     
    //换行  
    excel += row + "</tr>";  
    //设置数据
    for (var i = 0; i < arrData.length; i++) {  
        var row = "<tr>";  
        for (var index in arrData[i]) {  
            console.log(arrData[i][index]);
            //var value = arrData[i][index] === "." ? "" : arrData[i][index];  
            row += '<td>' + arrData[i][index] + '</td>';  
        }  
        excel += row + "</tr>";  
    }  

    excel += "</table>";  

    var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";  
    excelFile += '<meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">';  
    excelFile += '<meta http-equiv="content-type" content="application/vnd.ms-excel';  
    excelFile += '; charset=UTF-8">';  
    excelFile += "<head>";  
    excelFile += "<!--[if gte mso 9]>";  
    excelFile += "<xml>";  
    excelFile += "<x:ExcelWorkbook>";  
    excelFile += "<x:ExcelWorksheets>";  
    excelFile += "<x:ExcelWorksheet>";  
    excelFile += "<x:Name>";  
    excelFile += "{worksheet}";  
    excelFile += "</x:Name>";  
    excelFile += "<x:WorksheetOptions>";  
    excelFile += "<x:DisplayGridlines/>";  
    excelFile += "</x:WorksheetOptions>";  
    excelFile += "</x:ExcelWorksheet>";  
    excelFile += "</x:ExcelWorksheets>";  
    excelFile += "</x:ExcelWorkbook>";  
    excelFile += "</xml>";  
    excelFile += "<![endif]-->";  
    excelFile += "</head>";  
    excelFile += "<body>";  
    excelFile += excel;  
    excelFile += "</body>";  
    excelFile += "</html>";  

    var uri = 'data:application/vnd.ms-excel;charset=utf-8,' + encodeURIComponent(excelFile);  

    var link = document.createElement("a");      
    link.href = uri;  

    link.style = "visibility:hidden";  
    link.download = FileName + ".xls";  

    document.body.appendChild(link);  
    link.click();  
    document.body.removeChild(link);  
}
function isJSON(str) {
            try {  
                if (typeof JSON.parse(str) == "object") {  
                return true;  
                }  
            } catch(e) {  
            }  
            return false;  
        }
