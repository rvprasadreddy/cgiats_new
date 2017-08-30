;(function(angular){
		"use strict";
		
		angular.module("categorysubmittalsreport",[])
	    .controller("categorysubmittalscontroller",function($rootScope, $scope, blockUI, $http, $timeout, $filter, $mdDialog, $mdMedia, $window, $state,dateRangeService, $stateParams) {
	    	
	    	var candidateData = [];
			$scope.candidates = [];
			$scope.candidates.push({});
			//$scope.submittalTable = false;
	    	$scope.onload = function(){
	    		
	    		if($stateParams.dmName == "" || $stateParams.dmName == null || $stateParams.dmName == undefined)
				{
				categorysubmittals();
				}
	    		
	    	}
	    	
 	function categorysubmittals() {
	    		
	    		$scope.getobj = {
						"startDate" : $stateParams.startDate,
						"endDate" : $stateParams.endDate,
						"status" : $stateParams.status,
						"timePeriod" : $stateParams.timePeriod,
						"category" : $stateParams.category.replace("%2F","/")
				}
	    		
	    		
	      		var response = $http.post("customReports/getSubmittalsListByCategory",$scope.getobj);
				response.success(function(data, status,headers, config) 
						{
					candidateData = [];
					for (var i = 0; i < data.length; i++) {
						var candidateObj = data[i];
						candidateData.push(candidateObj);
						
					}
					dispalyTable();
						 });
				response.error(function(data, status, headers, config){
		  			  if(status == constants.FORBIDDEN){
		  				location.href = 'login.html';
		  			  }else{  			  
		  				$state.transitionTo("ErrorPage",{statusvalue  : status});
		  			  }
		  		  });
	    		
	    		//alert(JSON.stringify($scope.getobj));
 	}
 	$scope.submittalTable = true;
 	
 	function dispalyTable() {
		$scope.candidates
		.forEach(function(candidate, index) {
			candidate.submittalListBsTableControl = {
				options : {
					data : candidateData || {},
					striped : true,
					pagination : true,
					paginationVAlign : "both",
					pageSize : 10,
					pageList : [ 10, 20, 50 ],
					search : false,
					showColumns : false,
					showRefresh : false,
					clickToSelect : false,
					showToggle : false,
					maintainSelected : true,
					columns : [
							{
								field : 'createdOn',
								title : 'Created On',
								align : 'left',
								sortable : true
							},
							{
								field : 'updatedOn',
								title : 'Updated On',
								align : 'left',
								sortable : true
							},
							{
								field : 'createdBy',
								title : 'Created By',
								align : 'left',
								sortable : true
							},
							{
								field : 'status',
								title : 'Status',
								align : 'left',
								sortable : true
							},
							{
								field : 'candidateName',
								title : 'Candidate',
								align : 'left',
								sortable : true
							},
							/*{
								field : 'actions',
								title : 'Actions',
								align : 'left',
								sortable : false,
								events : window.submittalEvent,
								formatter : submittalEventActionFormatter
							}*/ ],

				}
			    };
		});
 	}
	    	
		});
		
})(angular);