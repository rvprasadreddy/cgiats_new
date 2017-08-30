;(function(angular){
	"use strict";
	
		
		angular.module("resumeAuditLogsReportModule",[])
			.service('resumeAuditSearchData', ['$http','$state', function ($http,$state) {
								this.reportsSearch = function(obj,number, size){
									$(".underlay").show();
									var response = $http.post("reportController/getResumeAuditLogData?pageNumber="+number+"&pageSize="+size,obj);
									response.success(function(data, status,headers, config) 
											{
										return data;
										
								      });
									response.error(function(data, status, headers, config){
					        			  if(status == constants.FORBIDDEN){
					        				location.href = 'login.html';
					        			  }else{  			  
					        				$state.transitionTo("ErrorPage",{statusvalue  : status});
					        			  }
					        		  });
									$(".underlay").hide();
									return response;
								}
							}])
		.controller("resumeauditreportcontroller", function(resumeAuditSearchData,$scope, $http,dateRangeService, $state){
	
			$scope.errMsg=false;
			$scope.resumeAuditLogInfo = [];
			$scope.candidateId = null;
			$scope.viewedBy = null;
			var startDateVal = new Date();
			startDateVal.setDate(1);
			$scope.startDate = startDateVal;
			$scope.endDate = new Date();
			$scope.onload = function(){
				$scope.DateFormat = 'MM-dd-yyyy';
				
				$scope.startDateOptions = {
						date : new Date(),
						showWeeks : false
					};
				$scope.startDateopen = function() {
					$scope.startDatePopup.opened = true;
				};
				$scope.startDatePopup = {
						opened : false
					};
				
				$scope.endDateOptions = {
						date : new Date(),
						showWeeks : false
					};
				$scope.endDateopen = function() {
					$scope.endDatePopup.opened = true;
				};
				$scope.endDatePopup = {
						opened : false
					};
				
				/*----------Pagination Details---------*/
				$scope.pageNumber = 1;
				$scope.pageSize = 10;
				$scope.orderName = "";
            	$scope.orderType = "";
				
				var startDate = dateRangeService.formatDate($scope.startDate);
				var endDate =  dateRangeService.formatDate($scope.endDate);
				var startTime = new Date($scope.startDate).getTime();
				var endTime = new Date($scope.endDate).getTime();
				$scope.obj = {"startDate" : startDate,
							"endDate" : endDate,
							"candidateId" : $scope.candidateId,
							"viewedBy" : $scope.viewedBy,
							"fieldName" : $scope.orderName,
							"sortName"  : $scope.orderType};
				
				
				resumeAuditSearchData.reportsSearch($scope.obj, $scope.pageNumber, $scope.pageSize).then(function(response){
					var data = response.data;
					$scope.resumeAuditLogTable = true;
					resumeAuditLogTableView();
					$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.data=data.resumeAuditLogData;
					$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.pageNumber =$scope.pageNumber;
					$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.pageSize =$scope.pageSize;
					$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.totalRows =data.resultCount;
					
				});
				//$scope.getResumeAuditLogs();
			}
			
		$scope.getResumeAuditLogs = function(){
			$(".underlay").show();
				$scope.resumeAuditLogTable = false;
				$scope.pageNumber = 1;
				$scope.pageSize = 10;
			    $scope.obj = {};
				var startTime = new Date($scope.startDate).getTime();
				var endTime = new Date($scope.endDate).getTime();
				var startDate = dateRangeService.formatDate($scope.startDate);
				var endDate =  dateRangeService.formatDate($scope.endDate);
				$scope.obj = {"startDate" : startDate,
						"endDate" : endDate,
						"candidateId" : $scope.candidateId,
						"viewedBy" : $scope.viewedBy,
						"fieldName" : $scope.orderName,
						"sortName"  : $scope.orderType
				};
				if(endTime >= startTime && startDate!=null && endDate!=null){
					$(".underlay").show();
					var response = $http.post("reportController/getResumeAuditLogData?pageNumber="+$scope.pageNumber+"&pageSize="+$scope.pageSize, $scope.obj);
					response.success(function(data, config, headers, status){
						//alert(JSON.stringify(data));
						if(data){
							$scope.errMsg=false;
//							$scope.clientWiseSbmList = data.gridData; 
//							$scope.exportClientWiseSbmList = [];
							
					/*		for (var i = 0; i <data.gridData.length; i++) {
								var obj = {jobOrderId:data.gridData[i].orderId,
										CreatedDate: data.gridData[i].createdOn,ClientName: data.gridData[i].jobClient,
										Title: data.gridData[i].jobTitle,DMName:data.gridData[i].dmName,AssignedTo: data.gridData[i].assignedTo,Positions: data.gridData[i].noOfPositions,
										SBM: data.gridData[i].submittedCount,Confirmed: data.gridData[i].confirmedCount,
										Started: data.gridData[i].startedCount,NetPositions: data.gridData[i].netPositions};
								$scope.exportClientWiseSbmList.push(obj);
							}*/
						//	$scope.$storage.pageNumber = $scope.pageNumber;
						//	$scope.$storage.pageSize = $scope.pageSize;
//							alert("data"+JSON.stringify(data.resultCount));
							$scope.resumeAuditLogTable = true;
							resumeAuditLogTableView();
							$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.data=data.resumeAuditLogData;
							$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.pageNumber =$scope.pageNumber;
							$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.pageSize =$scope.pageSize;
							$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.totalRows =data.resultCount;
						}else{
							$scope.resumeAuditLogTable = true;
							resumeAuditLogTableView();
						//	$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.data="";
						}
						$(".underlay").hide();
					});
					response.error(function(data, status, headers, config){
						  if(status == constants.FORBIDDEN){
							location.href = 'login.html';
						  }else{  			  
							$state.transitionTo("ErrorPage",{statusvalue  : status});
						  }
					  });
				//	$(".underlay").hide();
					}else{
						$scope.errMsg=true;
						$scope.resumeAuditLogTable = true;
					}
				
			}
		
			
			function resumeAuditLogTableView(){
				 $scope.resumeAuditLogInfo.resumeAuditLogTableControl = {
		                    options: { 
		                        striped: true,
		                        pagination: true,
		                        paginationVAlign: "both", 
		                        sidePagination : 'server',
		                        pageList: [10, 20, 50],
		                        search: false,
		                        silentSort: false,
		                        showColumns: false,
		                        showRefresh: false,
		                        clickToSelect: false,
		                        showToggle: false,
		                        maintainSelected: true, 
		                        showFooter : false,
		                        columns: [
		                        {
		                        	field: 'candidateId',
		                        	title: 'Candidate Id',
		                        	align: 'left',
		                        	
		                        },
		                        {
		                            field: 'createdBy',
		                            title: 'Viewed/Downloaded By',
		                            align: 'left',
		                            
		                        },{
		                            field: 'createdOn',
		                            title: 'Viewed/Downloaded Date',
		                            align: 'left',
		                            
		                        },{
		                            field: 'document_status',
		                            title: 'Document Status',
		                            align: 'left',
		                            
		                        },{
		                            field: 'status',
		                            title: 'Status',
		                            align: 'left',
		                           
		                        }],
		                        
		                        onPageChange: function (number, size) {
		                        	$(".underlay").show();
		                        	$scope.resumeAuditLogTable = false;
									 //  $(".underlay").show();
		                			var startDate = dateRangeService.formatDate($scope.startDate);
		                			var endDate =  dateRangeService.formatDate($scope.endDate);
									   $scope.pageNumber = number;
									   $scope.pageSize = size;
										if(startDate!=null && endDate!=null){
//											alert(JSON.stringify($scope.obj));
											var response = $http.post("reportController/getResumeAuditLogData?pageNumber="+number+"&pageSize="+size, $scope.obj);
											response.success(function(data, config, headers, status){
												if(data){
//													alert(JSON.stringify(data.resumeAuditLogData));
													$scope.errMsg=false;
												/*	$scope.clientWiseSbmList = data.gridData; 
													$scope.exportClientWiseSbmList = [];
													
													for (var i = 0; i <data.gridData.length; i++) {
														var obj = {jobOrderId:data.gridData[i].orderId,
																CreatedDate: data.gridData[i].createdOn,ClientName: data.gridData[i].jobClient,
																Title: data.gridData[i].jobTitle,DMName:data.gridData[i].dmName,AssignedTo: data.gridData[i].assignedTo,Positions: data.gridData[i].noOfPositions,
																SBM: data.gridData[i].submittedCount,Confirmed: data.gridData[i].confirmedCount,
																Started: data.gridData[i].startedCount,NetPositions: data.gridData[i].netPositions};
														$scope.exportClientWiseSbmList.push(obj);
													}*/
													$scope.resumeAuditLogTable = true;
													resumeAuditLogTableView();
													$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.data=data.resumeAuditLogData;
													$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.pageNumber =$scope.pageNumber;
													$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.pageSize =$scope.pageSize;
													$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.totalRows =data.resultCount;
													$(".underlay").hide();
												}else{
													alert('Empty');
//													$scope.resumeAuditLogTable = true;
													resumeAuditLogTableView();
											//		$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.data="";
												}
											});
											
										}
										response.error(function(data, status, headers, config){
											  if(status == constants.FORBIDDEN){
												location.href = 'login.html';
											  }else{  			  
												$state.transitionTo("ErrorPage",{statusvalue  : status});
											  }
										  });
										
		                        },
		                        onSort: function (name, order) {	
		                        	$(".underlay").show();
		                         	$scope.resumeAuditLogTable = false;
		                     
		                       	 if((!$scope.orderType || ($scope.orderName && $scope.orderName != name)) && !$scope.orderType == ""){
		                       		 $scope.orderType = order;
	                        	  }
		                       	 if($scope.orderType == ""){
		                       		 $scope.orderType = "asc"
		                       	 }
	                        	  if(($scope.orderName && $scope.orderName == name) ){
	                        		  if($scope.orderType && $scope.orderType == "asc"){
	                        			  $scope.orderType = "desc";
	                        		  }else{
	                        			  $scope.orderType = "asc";
	                        		  }
	                        	  }
	                        	  $scope.orderName = name;
		                        	var fields = $scope.obj;
									fields["fieldName"] = $scope.orderName;
									fields["sortName"] = $scope.orderType;
//									 alert(JSON.stringify(searchFileds));
									 var response = $http.post("reportController/getResumeAuditLogData?pageNumber="+$scope.pageNumber+"&pageSize="+ $scope.pageSize,fields);
										response.success(function(data, status,headers, config) 
												{
											if(data){
											$scope.errMsg=false;
											$scope.resumeAuditLogTable = true;
											resumeAuditLogTableView();
											$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.data=data.resumeAuditLogData;
											$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.pageNumber =$scope.pageNumber;
											$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.pageSize =$scope.pageSize;
											$scope.resumeAuditLogInfo.resumeAuditLogTableControl.options.totalRows =data.resultCount;
											$(".underlay").hide();
											}
										
									      });  
										response.error(function(data, status, headers, config){
											$(".underlay").hide();
						        			  if(status == constants.FORBIDDEN){
						        				location.href = 'login.html';
						        			  }else{  			  
						        				$state.transitionTo("ErrorPage",{statusvalue  : status});
						        			  }
						        		  });
							        }
				 
				 }
				 };
			}
		});
})(angular);