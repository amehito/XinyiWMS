let dateMap = new Map();
	dateMap.set("Jan","01");
	dateMap.set("Feb","02");
	dateMap.set("Mar","03");
	dateMap.set("Apr","04");
	dateMap.set("May","05");
	dateMap.set("Jun","06");
	dateMap.set("Jul","07");
	dateMap.set("Aug","08");
	dateMap.set("Sep","09");
	dateMap.set("Oct","10");
	dateMap.set("Nov","11");
	dateMap.set("Dec","12");
function toNumber(month){
	return dateMap.get(month);
}