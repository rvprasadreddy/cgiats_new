;(function(angular){
	
	
	angular.module("popuppd", ['angular-highlight'])
	.controller('candidatepopupcontroller', function ($scope, $window, $location, $http) {
		
	$scope.onload = function()
	{
		
		var pageurl = window.location;
		var candidateId = pageurl.toString().split("?")[1];
		var keyskill  = pageurl.toString().split("?")[2];
		var keyword = pageurl.toString().split("?")[3];
		if(keyskill){
		$scope.keyskills = keyskill.replace(/%20/g," ");
		$scope.keyskills = $scope.keyskills.replace(/%22/g,'\"');
		}
		if(keyword){
		$scope.keywords = keyword.replace(/%20/g," ");
		$scope.keywords = $scope.keywords.replace(/%22/g,'\"');
		}
		/*$scope.pageName = candidateId;
		var candidateId = $window.candId;
		$scope.keyskills = $window.keyskills;
		$scope.keywords = $window.keywords;*/
		var response = null;
		if(document.referrer.indexOf('cgiats')!=-1){
			response = $http.get('/cgiats/searchResume/viewCandidateDetails?candidateId='+ candidateId);
		}else{
			response = $http.get('/searchResume/viewCandidateDetails?candidateId='+ candidateId);
		}
response.success(function(data, status,headers, config) 
		{
	if(data){
	
			var resumecont = data.resumeContent;
			$scope.row = data;
			
			//$("#resumecontentdiv").html(resumecont);
			var candresume = JSON.stringify(data);
			
			 var viewObjResume = JSON.parse(candresume);
			
			$scope.viewCandidateText = viewObjResume.resumeContent;
			$scope.viewCandidateKeywords = null;
			if($scope.keyskills && $scope.keywords && $scope.keyskills != 'undefined' && $scope.keywords != 'undefined'){
				$scope.keyskillsAndKeyWords = $scope.keyskills +","+ $scope.keywords;
				}else if($scope.keywords && $scope.keywords != 'undefined'){
					$scope.keyskillsAndKeyWords =$scope.keywords;
				}else if($scope.keyskills && $scope.keyskills != 'undefined'){
					$scope.keyskillsAndKeyWords = $scope.keyskills;
				}
				if($scope.keyskillsAndKeyWords){
					$scope.keyskillsAndKeyWords = $scope.keyskillsAndKeyWords.toLowerCase();
				}
				
				var viewStrKeywords = $scope.keyskillsAndKeyWords;
				
				
//				alert(viewStrKeywords);
				if(viewStrKeywords){
					viewStrKeywords = viewStrKeywords.replace(/[*+:]/g, ",").replace(/[?]/g, ",").replace(/[!]/g, ",");
//					alert(viewStrKeywords);
					var keywords = viewStrKeywords.replace(/["']/g, ":-").split(/:/);
					if(keywords.length > 1){
						var key="";
					for(var k=0;k<keywords.length;k++){
						if(keywords[k] && keywords[k].length > 2 && keywords[k].charAt(1)!=' ' && keywords[k].indexOf('-') != -1 && keywords[k].indexOf('-') == 0){
							var requiredValue= keywords[k].substring(1,keywords[k].length);
							if(key!=""){
							key += ","+requiredValue.replace(/ /g, "_");
							}else{
								key += requiredValue.replace(/ /g, "_");
							}
						}else if(keywords[k] && keywords[k].length > 2  && keywords[k].indexOf('-') != -1 && keywords[k].indexOf('-') == 0){
							if(key!=""){
								key+=","+keywords[k].substring(2,keywords[k].length);
								}else{
									key+=keywords[k].substring(2,keywords[k].length);
								}
						}else if(keywords[k] && keywords[k].length > 2){
							if(key!=""){
								key+=","+keywords[k];
								}else{
									key+=keywords[k];
								}
						}
					}
					if(key.length>1){
					viewStrKeywords = key;
					}
					}
//					alert(viewStrKeywords);
					
				viewStrKeywords = viewStrKeywords.replace(/["']/g, "").replace(/[NOT]/g, "(").replace(/[(]/g, "").replace(/[)]/g, "");
				var viewStrKeywordsArray = viewStrKeywords.split(/[ \(,\)]+/);
				
				var indexOfAnd = -1;
				
//				alert(viewStrKeywordsArray);
				
				if(viewStrKeywordsArray){
					for(var i=0;i<viewStrKeywordsArray.length;i++){
//						alert(viewStrKeywordsArray[i]+' siva');
						viewStrKeywordsArray[i] = viewStrKeywordsArray[i].replace(/_/g, " ");
						viewStrKeywordsArray[i] = viewStrKeywordsArray[i].replace(/ or /g, ",");
						viewStrKeywordsArray[i] = viewStrKeywordsArray[i].replace(/or /g, ",");
						viewStrKeywordsArray[i] = viewStrKeywordsArray[i].replace(/ and /g, ",");
						viewStrKeywordsArray[i] = viewStrKeywordsArray[i].replace(/and /g, ",");
						indexOfAnd = viewStrKeywordsArray.indexOf("and");
						if(indexOfAnd != -1){
							viewStrKeywordsArray.splice( indexOfAnd, 1 );
						}
							indexOfAnd = viewStrKeywordsArray.indexOf("or");
							if(indexOfAnd != -1){
							viewStrKeywordsArray.splice( indexOfAnd, 1 );
							}
							indexOfAnd = viewStrKeywordsArray.indexOf("undefined");
							if(indexOfAnd != -1){
							viewStrKeywordsArray.splice( indexOfAnd, 1 );
							}
							
					}
				}
				
				for(var i=0;i<viewStrKeywordsArray.length;i++){
//					alert(viewStrKeywordsArray[i]);
					if(viewStrKeywordsArray[i] == constants.Dotnet){
						viewStrKeywordsArray[i] = constants.EscDotNet;
					}
				}
				
//				alert(viewStrKeywordsArray);
					viewStrKeywords = viewStrKeywordsArray.join(',');
					viewStrKeywords = viewStrKeywords.replace(/[,]+/g, ",")
					viewStrKeywords = viewStrKeywords.replace(/^,|,$/g,'');
//					alert(viewStrKeywords);
					
					$scope.viewCandidateKeywords = viewStrKeywords;
					 var highlightRe = /<span class="highlight">(.*?)<\/span>/g,
					    highlightHtml = '<span class="highlight">$1</span>';

					
					 var keywordArray = viewStrKeywords.split(',');
					 for(var j=0;j<keywordArray.length;j++){
						 var term = keywordArray[j];
//						 alert(term);
					        if(term  && term.length>0) {
					        	$scope.viewCandidateText = $scope.viewCandidateText.replace(new RegExp('(' + term + ')', 'gi'), highlightHtml);
					        }  
					       
					 }
				}
				
				
	}else{
	}
	
		});
response.error(function(data, status, headers, config){
	  if(status == constants.FORBIDDEN){
		location.href = 'login.html';
	  }else{  			  
//		$state.transitionTo("ErrorPage",{statusvalue  : status});
	  }
  });





		
		
		
		
	}
		
	});
	
})(angular)