var constants = {
		ADM                  					: 'ADM',
		Administrator  							: 'Administrator',
		Manager 								: 'Manager',
		AccountManager 							: 'AccountManager',
		EM										: 'EM',
		DivisionHead							: 'DivisionHead',
		ATS_Executive							: 'ATS_Executive',
		DM										: 'DM',
		HR										: 'HR',
		Recruiter								: 'Recruiter',
		IN_Recruiter							: 'IN_Recruiter',
		IN_DM									: 'IN_DM',
		IN_TL									: 'IN_TL',
		HIGH									: 'HIGH',
		MEDIUM									: 'MEDIUM',
		LOW										: 'LOW',
		PENDING									: 'PENDING',
		OPEN									: 'OPEN',
		ASSIGNED								: 'ASSIGNED',
		HOLD									: 'HOLD',
		FILLED									: 'FILLED',
		CLOSED									: 'CLOSED',
		REOPEN									: 'REOPEN',
		SUBMITTED								: 'SUBMITTED',
		CONTRACT								: 'CONTRACT',
		PERMANENT								: 'PERMANENT',
		BOTH									: 'BOTH',
		NOTSPECIFIED							: 'NOTSPECIFIED',
		
		HOTJOBORDERS							: 'hotJobOrders',
		
		ALLJOBORDERS							: 'allJobOrders',
		MYJOBORDERS								: 'myJobOrders',
		PENDINGJOBORDERS						: 'pendingJobOrders',
		RECRUITERSREPORT						: 'recruitersreportmodule',
		EMJOBORDERS								: 'emJobOrders',
		HOTJOBORDERS							: 'hotJobOrders',
		DMJOBORDERSREPORT						: 'dmjobordersreport/:dmname?startDate?endDate',
		OFFER_LETTER_CREATED					: 'OFFER_LETTER_CREATED',
		category1								: 'category1',
		Yes										: 'Yes',
		No										: 'No',
		OFFER_LETTER_REQUEST_NOT_RECEIVED		: 'OFFER_LETTER_REQUEST_NOT_RECEIVED',
		OFFER_LETTER_REQUEST_RECEIVED			: 'OFFER_LETTER_REQUEST_RECEIVED',	
		OFFER_LETTER_SENT_TO_CONSULTANT 		: 'OFFER_LETTER_SENT_TO_CONSULTANT',
		BACKGROUND_CHECK_INITIATED 				: 'BACKGROUND_CHECK_INITIATED',
		DRUG_TEST_INITIATED 					: 'DRUG_TEST_INITIATED',
		SIGNED_OFFER_RECEIVED 					: 'SIGNED_OFFER_RECEIVED',
		BENEFITS_ENROLLMENT_FORMS_BEING_SENT_OUT: 'BENEFITS_ENROLLMENT_FORMS_BEING_SENT_OUT',
		BENEFITS_ENROLLMENT_FORMS_NOT_RECEIVED 	: 'BENEFITS_ENROLLMENT_FORMS_NOT_RECEIVED',
		BENEFITS_ENROLLMENT_FORMS_RECEIVED 		: 'BENEFITS_ENROLLMENT_FORMS_RECEIVED',
		CANDIDATE_ON_BOARDED 					: 'CANDIDATE_ON_BOARDED',
		CANDIDATE_BACKED_OUT 					: 'CANDIDATE_BACKED_OUT',
		FIRST_CONTACT_MADE_PRE_OFFER_DISCUSSION : 'FIRST_CONTACT_MADE_PRE_OFFER_DISCUSSION',
		OFFER_LETTER_SAVED						: 'OFFER_LETTER_SAVED',
		OFFER_LETTER_SUBMITTED					: 'OFFER_LETTER_SUBMITTED',
		TODAY									: 'Today',
		LAST_ONE_MONTH							: 'Last 1 month',
		LAST_THREE_MONTHS						: 'Last 3 months',
		LAST_SIX_MONTHS							: 'Last 6 months',
		LAST_ONE_YEAR							: 'Last 1 year',
		ALLTIME									: 'All Time',
		CUSTOM_RANGE							: 'Custom Range',
		
		ALL_TIME								: 'AllTime',
		THIRTY_DAYS								: 'Last30Days',
		NINTY_DAYS								: 'Last90Days',
		CUSTOME									: 'Custom',
		TO_DAY									: 'Today',
		
		ASC										:  'ASC',
		DESC									:  'DESC',
		
		FORBIDDEN								:  403,
		
		ORGDOC 									: 'ORG_DOC',
		RTRDOC 									: 'RTR_DOC',
		PROCDOC 								: 'PROC_DOC',
		MS_WORD									: 'MS_WORD',
		DOCX									: 'DOCX',
		PLAIN									: 'PLAIN',
		RTF										: 'RTF',
		HTML									: 'HTML',
		PDF										: 'PDF',
		
		Dotnet									: '.net',
		EscDotNet								: '\\.net',
		
		MYINDIAJOBORDERS							:'myIndiaJobOrders',
		ALLINDIAJOBORDERS						:'allIndiaJobOrders',
		INDIASUBMITTALSTATUSMODULE				:'indiaSubmitalStatsModule',
		
		
		DirectCustomerRelationship              : "A-Direct Customer/ Relationship",
		VMSPortal								: "B-VMS Portal",
		ThirdParty								: "C-Third Party",
		
		USERROLE								: 'userRole',
		OFFICELOCATION							: 'officeLocation',
		MISSING_DATA							: 'missingData',
        HYD										: 'HYD',
        ALL										: 'All',
        OTHER									: 'Other',
        LOGIN									: 'login',
        LOGOUT									: 'logout',
        ACTIVE									: 'ACTIVE',
        ANIL_USER_ID       						:  'anils',
        
        RVEMULA_USER_ID      					:  'rvemula',
        HARI_USER_ID      			 			:  'Hari',
        
        SRINIB_USER_ID      					:  'sriniB',
        VIJAY_USER_ID      						:  'vtarikonda',
        MURALI_USER_ID     						:  'Mreddy',
        DEVEN_USER_ID      						:  'dreddy',
        KEN_USER_ID       						:  'Ken'
};

var sourceConst = {
		CareerBuilder            :  'Career Builder',
		Monster					 :  'Monster',
		Dice					 :  'Dice',
		TechFetch				 :  'Tech Fetch',
		OnlineResumes			 :  'Online Resumes',
		MobileResumes        	 :  'Mobile Resumes'
};

var resumeSourceNames = ['Dice',
                         'Monster',
                         'Careerbuilder',
                         'Techfetch',
                         'Sapeare',
                         'Sapeare Portal',
                         'CGI',
                         'CGI Portal',
                         'RedGalaxy',
                         'RedGalaxy Portal'
                         ];

var indiaStates =['Andaman and Nicobar Islands',
                  'Andhra Pradesh',
                  'Arunachal Pradesh',
                  'Assam',
                  'Bihar',
                  'Chandigarh',
                  'Chhattisgarh',
                  'Dadra and Nagar Haveli',
                  'Daman and Diu',
                  'Delhi',
                  'Goa',
                  'Gujarat',
                  'Haryana',
                  'Himachal Pradesh',
                  'Jammu and Kashmir',
                  'Jharkhand',
                  'Karnataka',
                  'Kerala',
                  'Lakshadweep',
                  'Madhya Pradesh',
                  'Maharashtra',
                  'Manipur',
                  'Meghalaya',
                  'Mizoram',
                  'Nagaland',
                  'Orissa',
                  'Pondicherry',
                  'Punjab',
                  'Rajasthan',
                  'Sikkim',
                  'Telangana',
                  'Tamil Nadu',
                  'Tripura',
                  'Uttaranchal',
                  'Uttar Pradesh',
                  'West Bengal'
                  ];

var usStates ={
		'Alabama, AL'       :  'AL',
		'Alaska, AK'        :  'Ak',
		'Arizona, AZ'       :  'AZ',
		'Arkansas, AR'      :  'AR',
		'California, CA'    :  'CA',
		'Colorado, CO'      :  'CO',
		'Connecticut, CT'   :  'CT',
		'Delaware, DE'      :  'DE',
		'District of Columbia, DC'        :  'DC',
		'Florida, FL'       :  'FL',
		'Georgia, GA'       :  'GA',
		'Hawaii, HI'        :  'HI',
		'Idaho, ID'         :  'ID',
		'Illinois, IL' 	    :  'IL',
		'Indiana, IN'       :  'IN',
		'Iowa, IA'          :  'IA',
		'Kansas, KS'        :  'KS',
		'Kentucky, KY'      :  'KY',
		'Louisiana, LA'     :  'LA',
		'Massachusetts, MA' :  'MA',
		'Maryland, MD'      :  'MD',
		'Maine, ME'         :  'ME',
		'Michigan, MI'      :  'MI',
		'Minnesota, MN'     :  'MN',
		'Missouri, MO'      :  'MO',
		'Mississippi, MS'   :  'MS',
		'Montana, MT'       :  'MT',
		'North Carolina, NC':  'NC',
		'North Dakota, ND'  :  'ND',
		'Nebraska, NE'      :  'NE',
		'New Hampshire, NH' :  'NH',
		'New Jersey, NJ'    :  'NJ',
		'New Mexico, NM'    :  'NM',
		'Nevada, NV'        :  'NV',
		'New York, NY'      :  'NY',
		'Ohio, OH'          :  'OH',
		'Oklahoma, OK'      :  'OK',
		'Oregon, OR'        :  'OR',
		'Pennsylvania, PA'  :  'PA',
		'Rhode Island, RI'  :  'RI',
		'South Carolina, SC':  'SC',
		'South Dakota, SD'  :  'SD',
		'Tennessee, TN'     :  'TN',
		'Texas, TX'         :  'TX',
		'Utah, UT'          :  'UT',
		'Virginia, VA'      :  'VA',
		'Vermont, VT'       :  'VT',
		'Washington, WA'    :  'WA',
		'Wisconsin, WI'     :  'WI',
		'West Virginia, WV' :  'WV',
		'Wyoming, WY'       :  'WY',
		'Others'            :  'Others'
		
		
 
}
var smsService = {
	to	         :'&To=',
	from         : '&From=27126',
	userId       : '&UserId=cgiats',
	password     :  '&Password=charter123',
	vasId        : '&vasid=8753',
	networkId    : '&networkid=44',
	profileId    : '&profileid=2',
	text         : '&Text='
};