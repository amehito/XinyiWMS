function format(search_user_id){
	search_user_id = search_user_id.trim()
	search_user_id = search_user_id.replace(/\*/g,'\\*')
	return search_user_id;
}

function tableSearch(item,search_id){
	search_id = format(search_id)
	for(let key in item){ 
		tempName = item[key]
		if(item[key] == null || item[key] == undefined){
			continue;
		}
		try {
			if(item[key] == search_id || item[key].search(search_id)!=-1){
				return true;
			}
		} catch (e) {
										
			// TODO: handle exception
		}
	}
	return false;
}
