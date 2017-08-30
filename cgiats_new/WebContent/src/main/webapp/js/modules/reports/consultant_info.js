;(function(angular){
	"use strict";	
	angular.module("consultantinfopage",['ui.bootstrap'])
	.controller("consultantinfocontroller",function($rootScope, $scope, blockUI, $http, $timeout, $location,
			$filter, $window, $state, $stateParams, $sessionStorage){
		
		$scope.currentDateWithTime = new Date();
		$scope.clientTable = false;
		$scope.candidate = [];
		$scope.onload = function()
		{
			//alert("$rootScope.candidateid  "+$rootScope.candidateid);
			//alert("$rootScope.candidateinfo id  "+$rootScope.candidateinfoid);
			$(".underlay").show();
			var response = $http.post("consultantInfo/getAllCandidateIds");
			response.success(function(data, status, headers, config){
//				alert("success data"+JSON.stringify(data));
				
				 $scope.candidateIds = data?data:[];
				 
				 $scope.candidateIds.sort(function(a, b){
					  return a.fullName.toLowerCase().trim() == b.fullName.toLowerCase().trim() ? 0 : +(a.fullName.toLowerCase().trim() > b.fullName.toLowerCase().trim()) || -1;
					});
			});
			response.error(function(data, status, headers, config){
	  			  if(status == constants.FORBIDDEN){
	  				location.href = 'login.html';
	  			  }else{  			  
	  				$state.transitionTo("ErrorPage",{statusvalue  : status});
	  			  }
	  		  });
			
			var response = $http.post("consultantInfo/getAllClientInfo");
			response.success(function(data, status, headers, config){
				//alert("success data"+JSON.stringify(data));
				
				$scope.exportConsultantInfoList=[];
				$scope.consultantInfoList = data?data:[];
				angular.forEach(data, function(obj, key){
					if(obj.salaryRate)
						{
						obj.salaryRate = obj.salaryRate.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
						obj.salaryRate = '$ ' + obj.salaryRate;
						}
					else
						{
						}
					var exportObj = {candidateId:obj.candidateId,jobOrderId:obj.jobOrderId,fullName:obj.fullName,preferredName:obj.preferredName,dateOfLastContact:obj.dateOfLastContact,email:obj.email,candidatePhone:obj.candidatePhone,dateOfBirth:obj.dateOfBirth,city:obj.city,state:obj.state,billingPartner:obj.billingPartner,clientName:obj.clientName,empRole:obj.empRole,bdmName:obj.bdmName,startDate:obj.startDate,endDate:obj.endDate,currentStatus:obj.currentStatus,rating:obj.rating,notes:obj.notes,referralNotes:obj.referralNotes,glassDoorReview:(obj.glassDoorReview?'Yes':'No')};
					if(exportObj.email){
						exportObj.email = exportObj.email.replace(';',' & ');
					}
					$scope.exportConsultantInfoList.push(exportObj);
				});
				
				$scope.clientTable = true;
				
				dispalyTable();
				$scope.candidate.bsTableControl.options.data =data; 
				$(".underlay").hide();
				if($rootScope.candidateids !=null && $stateParams.saveOrUpdate || $rootScope.candidateids != undefined && $stateParams.saveOrUpdate){
					if($rootScope.candidateinfoid != null || $rootScope.candidateinfoid != undefined){
						$.growl.success({title : "Info !", message : "Project Details updated Successfully"});
					}else{
						$.growl.success({title : "Info !", message : "Project Details Saved Successfully"});
					}
					$rootScope.candidateids = null;
					$rootScope.candidateinfoid = null;
				}
				
			});
			response.error(function(data, status, headers, config){
	  			  if(status == constants.FORBIDDEN){
	  				location.href = 'login.html';
	  			  }else{  			  
	  				$state.transitionTo("ErrorPage",{statusvalue  : status});
	  			  }
	  		  });
			
			
		}
		
		$scope.fillprojdetails = function()
		{
			$rootScope.candidateids = $scope.candidateid;
			$state.transitionTo("fillprojectdetails",{id : $scope.candidateid});
		}
		
		
		function dispalyTable() {
			//alert("pagining data"+JSON.stringify(pagingData));
			
			
						$scope.candidate.bsTableControl = {
							options : {
								pagination : true,
								paginationVAlign : "top",
								sidePagination : 'client',
								silentSort: false,
								pageList : [ 10, 20, 50 ],
								search : false,
								//showFooter : true,
								showColumns : false,
								showRefresh : false,
								clickToSelect : false,
								showToggle : false,
								columns : [
										{
											field : 'candidateId',
											title : 'Candidate Id',
											align : 'left',
											events : window.consultantOperateEvents,
											formatter : consultantActionFormatter,
											sortable : true
										},
										{
											field : 'jobOrderId',
											title : 'Job Order Id',
											align : 'left',

											sortable : true
										},
										{
											field : 'candidateInfoId',
											title : 'candidate Info Id',
											align : 'left',
											visible : false,
											sortable : true
										},
										{
											field : 'fullName',
											title : 'Candidate Name',
											align : 'left',

											sortable : true
										},{
											field : 'preferredName',
											title : 'Preferred Name',
											align : 'left',

											sortable : true
										},{
											field : 'dateOfLastContact',
											title : 'Last Contact',
											align : 'left',

											sortable : true
										},{
											field : 'email',
											title : 'Email',
											align : 'left',

											sortable : true
										},{
											field : 'candidatePhone',
											title : 'Phone #',
											align : 'left',

											sortable : true
										},{
											field : 'dateOfBirth',
											title : 'Birthday',
											align : 'left',

											sortable : true
										},{
											field : 'city',
											title : 'City',
											align : 'left',

											sortable : true
										},{
											field : 'state',
											title : 'State',
											align : 'left',

											sortable : true
										},{
											field : 'billingPartner',
											title : 'Billing Partner',
											align : 'left',

											sortable : true
										},
										
										{
											field : 'clientName',
											title : 'Client Name',
											align : 'left',
											sortable : true
										},
										{
											field : 'empRole',
											title : 'Role',
											align : 'left',
											sortable : true
										},{
											field : 'bdmName',
											title : 'DM Name',
											align : 'left',
											sortable : true
										},
										{
											field : 'startDate',
											title : 'Started Date',
											align : 'left',
											sortable : true
										},
										{
											field : 'endDate',
											title : 'End Date',
											align : 'left',
											sortable : true
										},
										{
											field : 'currentStatus',
											title : 'Status',
											align : 'left',
											sortable : true
										},
										{
											field : 'rating',
											title : 'Rating',
											align : 'left',
											sortable : true
										},
										{
											field : 'notes',
											title : 'notes',
											align : 'left',
											sortable : true
										} ,
										{
											field : 'referralNotes',
											title : 'Referral Program',
											align : 'left',
											sortable : true
										},
										{
											field : 'glassDoorReview',
											title : 'Glassdoor Review',
											align : 'center',
											formatter : glassDoorFormatter
										}],
										
							
							}
						};
						
						function glassDoorFormatter(value, row, index) {  
							 
					        return [ 
					        (row.glassDoorReview)?'<i class="fa fa-check" aria-hidden="true" style="color:green;font-size:14px;"></i>':'<i class="fa fa-times" aria-hidden="true" style="color:red;font-size:14px;"></i>', 
					        ].join(''); 
							 
					     }
						
						function consultantActionFormatter(value, row, index) {
								return ['<a class="candidateId actionIcons" title="'+ row.candidateId+ '" flex-gt-md="auto">'+ row.candidateId+'</a>']
								.join('');
						}
						window.consultantOperateEvents = {
							'click .candidateId' : function(e,value, row, index) {
								//alert("row data "+JSON.stringify(row));
								$rootScope.candidateids = row.candidateId;
								$rootScope.candidateinfoid = row.candidateInfoId;
								$state.transitionTo("fillprojectdetails",{id : row.candidateId, infoId : row.candidateInfoId});
							},
						}
		}
		
	});
	
	
})(angular);