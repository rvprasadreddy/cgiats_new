;(function(angular){
	"use strict";
	
		
		angular.module("consultantreportmodule",[])
		.controller("consultantreportcontroller", function($scope, $http, $timeout, dateRangeService, $state,$filter){
			
			$scope.consultantInfoReport=[];
			$scope.consultantInfoReportTable=false; 
			
			$scope.consultantreport = {};
				$scope.onload = function()
				{
					
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
						
					
					if($scope.dmRangeindm){
						$scope.consultantreport.startDate = dateRangeService.convertStringToDate($scope.dmRangeindm.startDate);;
						$scope.consultantreport.endDate = dateRangeService.convertStringToDate($scope.dmRangeindm.endDate);;
					}else{
						var startDateVal = new Date();
						startDateVal.setDate(1);
						$scope.consultantreport.startDate = startDateVal;
						$scope.consultantreport.endDate = new Date();
					}
					
					
					$(".caret").addClass("fa fa-caret-down");
					$(".caret").css("font-size", "16px");
					$(".caret").css("color", "#1895ab");
					$scope.getAllUsers();
				}
				 
				$scope.getTimeFnc = function(){
					$scope.currentDateWithTime = new Date();
				}
						
/*-*************************** GET USERS FUNCTION STARTS HERE********************************************-*/
						$scope.getAllUsers = function(){
							var response = $http.get('commonController/getAllDMAndADM_OfficeLocations?isAuthRequired='+true+"&isActive="+true);
							response.success(function (data,status,headers,config){
							//	alert(JSON.stringify(data));
								if(data){
								$scope.userRecords = data;
								$scope.uesrList = [];
								for(var i=0; i<data.users.length; i++ )
									{
										var obj = {id: data.users[i].userId, label: data.users[i].fullName};
										$scope.uesrList.push(obj);
									}
								$scope.dm = [];
//								$scope.dm = [{id: data.users[0].userId, label: data.users[0].fullName}];
								$scope.dmdata = $scope.uesrList;
								$scope.getdataforreport();
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
/*-*************************** GET USERS FUNCTION ENDS HERE********************************************-*/
						
				
				

				

				
/*-*************************** GET DATA FOR REPORT FUNCTION ENDS HERE********************************************-*/				
						
						$scope.getdataforreport = function(){
							
							$scope.consultantInfoReportTable=false; 
							
							
							var from = dateRangeService.formatDate($scope.consultantreport.startDate);
							var to = dateRangeService.formatDate($scope.consultantreport.endDate);
							
							$scope.dmforReport = "";
					
							for(var i=0; i < $scope.dm.length; i++)
							{
							$scope.dmforReport += $scope.dm[i].id + ", ";
							}
							
							var obj = {
									"startDate" : from,
									"endDate" : to,
									"dmName" : $scope.dmforReport
							}
							//alert(JSON.stringify(obj));
							
							var response = $http.post("customReports/getConsultantInfoReport", obj);
							response.success(function (data,status,headers,config){
								consultantInfoReportTableView();
								$scope.exportableDate=[];
								$scope.consultantInfoReport.consultantTableControl.options.data=[];
								$scope.consultantInfoReportTable=true;
								if(data){
									$scope.consultantInfoReport.consultantTableControl.options.data = data;
									for(var i=0;i<data.length;i++){
										var newObj = {candidateName:data[i].candidateName,clientName:data[i].clientName,jobCategory:data[i].jobCategory,startDate:data[i].startDate,endDate:data[i].endDate,status:data[i].status,recName:data[i].recName,dmName:data[i].dmName};
										$scope.exportableDate.push(newObj);
									}
									console.info(JSON.stringify($scope.exportableDate));
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
				
						$scope.exportData = function(){
							var mystyle = {         
							        headers:true,        
							        columns: [  
							          { columnid: 'candidateName', title: 'Consultant Name'},  
							          { columnid: 'clientName', title: 'Account Name'},  
							          { columnid: 'jobCategory', title: 'Job Category'},  
							          { columnid: 'startDate', title: 'Start Date'},  
							          { columnid: 'endDate', title: 'End Date' },  
							          { columnid: 'status', title: 'Status'},  
							          { columnid: 'recName', title: 'Recruiter Name'},  
							          { columnid: 'dmName', title: 'DM Name'},  
							        ],         
							    };  
							  
							// function to use on ng-click.  
							//$scope.searchCaseResult is your json data which cominmg from database or Controller.  
							  
							        //get current system date.         
							        var date = new Date();  
							        $scope.CurrentDateTime = $filter('date')(new Date().getTime(), 'MM/dd/yyyy HH:mm:ss');          
							        //To convert Date[mm/dd/yyyy] from jsonDate "/Date(1355287447277)/"  
							       /* for(var i=0; i<$scope.searchCaseResult.length;i++)  
							        {  
							            $scope.searchCaseResult[i].DocCreatedDate;  
							            var dateString = $scope.searchCaseResult[i].DocCreatedDate.substr(6);  
							            var currentTime = new Date(parseInt(dateString));  
							            var month = currentTime.getMonth() + 1;  
							            var day = currentTime.getDate();  
							            var year = currentTime.getFullYear();  
							            var date = month + "/" + day + "/" + year;  
							            $scope.exportableDate[i].DocCreatedDate = date;              
							        }  */
							        //Create XLS format using alasql.js file.  
							        alasql('SELECT * INTO XLS("consultant_info_report_' + $scope.CurrentDateTime + '.xls",?) FROM ?', [mystyle, $scope.exportableDate]);  
						}
						
/*-*************************** GET DATA FOR REPORT FUNCTION ENDS HERE********************************************-*/
				
				
/*-************************************************** TABLE FUNCTION ENDS HERE********************************************-*/
				function consultantInfoReportTableView(){
					 $scope.consultantInfoReport.consultantTableControl = {
			                    options: { 
			                        striped: true,
			                        pagination: true,
			                        paginationVAlign: "bottom", 
			                        pageList: [50,100,200],
			                        search: false,
			                        //sidePagination : 'server',
			                        silentSort: false,
			                        showColumns: false,
			                        showRefresh: false,
			                        clickToSelect: false,
			                        showToggle: false,
			                        maintainSelected: true, 
			                        showFooter : false,
			                        columns: [
			                        {
			                            field: 'candidateName',
			                            title: 'Consultant Name',
			                            align: 'left',
			                            
			                        },{
			                            field: 'clientName',
			                            title: 'Account Name',
			                            align: 'left',
			                            
			                        },{
			                            field: 'jobCategory',
			                            title: 'Job Category',
			                            align: 'left',
			                            
			                        },{
			                            field: 'startDate',
			                            title: 'Start Date',
			                            align: 'left',
			                           
			                        },{
			                            field: 'endDate',
			                            title: 'End Date',
			                            align: 'left',
			                           
			                        },{
			                            field: 'status',
			                            title: 'Status',
			                            align: 'left',
			                           
			                        },{
			                            field: 'recName',
			                            title: 'Recruiter Name',
			                            align: 'left',
			                           
			                        },{
			                            field: 'dmName',
			                            title: 'DM Name',
			                            align: 'left',
			                           
			                        }]
			                    }
			            };
				}
/*-************************************************** TABLE FUNCTION ENDS HERE********************************************-*/
				
				

				
				
				$scope.manysettings = {
			            smartButtonMaxItems: 3,
			            smartButtonTextConverter: function(itemText, originalItem) {
			                return itemText;
			            },
			        };
				
				
				$scope.manysearchsettings = {
						enableSearch: true,
			            smartButtonMaxItems: 3,
			            smartButtonTextConverter: function(itemText, originalItem) {
			                return itemText;
			            },
			        };
				
				$scope.onesettings = {
					    selectionLimit: 1,
					    smartButtonMaxItems: 1,
					             smartButtonTextConverter: function(itemText, originalItem) {
					                 return itemText;
					             },
					         };
	
		
				$scope.onesearchsettings = {
					    selectionLimit: 1,
					    enableSearch: true,
					    smartButtonMaxItems: 1,
					             smartButtonTextConverter: function(itemText, originalItem) {
					                 return itemText;
					             },
					         };
				
				
				
				
			
			$scope.showfilters = function()
			{
				if($("#filterfields").is(":visible"))
					{
					$("#filterfields").slideUp();
					}
				else
					{
					$("#filterfields").slideDown();
					}
				
			}
			
		});
})(angular);