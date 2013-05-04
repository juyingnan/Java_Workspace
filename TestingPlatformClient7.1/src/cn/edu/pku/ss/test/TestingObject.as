package cn.edu.pku.ss.test
{

	import cn.edu.pku.ss.test.common.*;
	
	import flash.display.DisplayObject;
	import flash.events.*;
	import flash.net.*;
	
	import mx.controls.Alert;
	import mx.core.*;
	import mx.managers.PopUpManager;
	import mx.rpc.events.*;
	import mx.rpc.http.HTTPService;
	import mx.rpc.soap.LoadEvent;
	import mx.rpc.soap.WebService;

	[Event(name="LoadingComplete", type="flash.events.Event")]
	[Event(name="TestingComplete", type="flash.events.Event")]
	public class TestingObject extends EventDispatcher
	{
		public static const LOADINGEVENT:String = "LoadingComplete";
		public static const TESTINGEVENT:String = "TestingComplete"
		
		public static const CPPAST:int = 0;
		public static const CPPBPSTUMP:int = 1;
		public static const CPPBPTESTING:int = 2;
		public static const CPPCONDITIONSTUMP:int = 3;
		public static const CPPCONDITIONTESTING:int = 4;
		public static const JAVAAST:int = 5;
		public static const JAVABPSTUMP:int = 6;
		public static const JAVABPTESTING:int = 7;
		public static const JAVACONDITIONSTUMP:int = 8;
		public static const JAVACONDITIONTESTING:int = 9;
		public static const CPPFUNCTION:int = 10;
		public static const JAVAMETHOD:int = 11;
		public static const JAVA:int = 1;
		public static const CPP:int = 0;
		public static const CODESTRUCT:int = 12;
		public static const JAVAGETFLOWGRAPH:int = 13;
		 
        public static const arrTestingType:Array = new Array(  
                  "DecisionCoverage","ConditionCoverage",
                  "MutipleCoverage","AllCoverage"); 
		
		public static var Instance:TestingObject = new TestingObject();
		
		
		public var TestingType:int = 0;
		public var UploadUrl:String = "";
		public var ServiceUrl:String = "";
		public var Result:String = "";
		public var TestingService:WebService = null;
		public var IsReady:Boolean = false;
		public var Session:String = "";
		public var FileName:String = "";
		public var Pop:IFlexDisplayObject = null;
		public var ClassName:String = "";
		public var Method:String = "";
		public var Options:String = "";
		
		protected var Code:String = "";
		protected var bp:int = -1;
		protected var fg:Boolean = false;
		protected var condition:int = -1;
		protected var conditionType:String = "";
		
		/**
		 * 0 = C++, 1 =Java
		 * */
		protected var sourceType:int =0;
	
		public function TestingObject(){
			//setStrategyAST();
		}
	
	
		/**
		 * Strategy setup
		 * */
		public function setSourceType(v:int):void{
			sourceType = v;
			
			if(ast){
				setStrategyAST();
			}else if(fg){
				setStrategyFlowGraph();
			}else if(bp>-1){
				setStrategyBP(bp);
			} else if(condition>-1){
				setStrategyCondition(condition);
			}
		}
		
		public function getSourceType():int{
			return sourceType;
		}
	
		/**
		 * Source type
		 * 
		 * */
		protected var ast:Boolean = true;
	
		public function isAST():Boolean{
			return ast;
		}
	
		public function setStrategyAST():void{
			if(TestingObject.Instance.getSourceType()==CPP){
            	TestingObject.Instance.TestingType = CPPAST;
            } else {
            	TestingObject.Instance.TestingType = JAVAAST;            	
            }
            ast = true;
            bp = -1;
			condition = -1;
		}
		
		public function setStrategyBP(v:int):void{
			if(TestingObject.Instance.getSourceType()==CPP){		
            	//CPPBPSTUMP or CPPBPTETING
				TestingObject.Instance.TestingType = CPPBPSTUMP + v;			
            } else {
            	//JAVABPSTUMP or JAVABPTETING
				TestingObject.Instance.TestingType = JAVABPSTUMP + v;	
            }
            ast = false;
            bp = v;
			condition = -1;
		}
		
		//select the funtion of displaying the flow graph
		public function setStrategyFlowGraph():void{
			if(TestingObject.Instance.getSourceType()==CPP){
				TestingObject.Instance.TestingType = JAVAGETFLOWGRAPH;
			} else {
				TestingObject.Instance.TestingType = JAVAGETFLOWGRAPH;            	
			}
			
			ast = false;
			fg = true;
			bp = -1;
			condition = -1;
		}
	
		public function setStrategyCondition(v:int):void{
			if(TestingObject.Instance.getSourceType()==CPP){
				if(v==0){
					TestingObject.Instance.TestingType = CPPCONDITIONSTUMP;
				} else {
					TestingObject.Instance.TestingType = CPPCONDITIONTESTING;
					conditionType = arrTestingType[v-1].toString();
					//Alert.show(conditionType);
				}
            	
            } else {            	
            	if(v==0){
					TestingObject.Instance.TestingType = JAVACONDITIONSTUMP;
				} else {
					TestingObject.Instance.TestingType = JAVACONDITIONTESTING;
					conditionType = arrTestingType[v-1].toString();
					//Alert.show(conditionType);
				}
            }
            ast = false;
            bp = -1;
			condition = v;
		}
		
		public function setCode(code:String):void{
			this.Code = code;
		}
		
		public function getCode():String{
			return Code;
		}
		
		/**
		 * 第一步,开始初始化
		 * */
		public function initialize():void{
 			var service:HTTPService = new HTTPService();
			service.url="config.xml";
			service.addEventListener(ResultEvent.RESULT,onConfigLoadHandler);
			service.send();
		}
		
		/**
		 * 第二步,读取配置文件,初始化WebService
		 * */
		private function onConfigLoadHandler(event:ResultEvent): void{
            //上传文件的Servlet地址
            //Configuration/UploadURL
            UploadUrl = event.result.Configuration.UploadURL;
            //Web Service wsdl 地址
            //Configuration/TestingService
            //ServiceUrl = event.result.Configuration.TestingService;
            ServiceUrl = "testing.wsdl";
           
			//Alert.show(testingServiceUrl);
				
			TestingService = new WebService();
			TestingService.wsdl = ServiceUrl;			
			TestingService.addEventListener(LoadEvent.LOAD, onServiceLoadComplete);
			TestingService.addEventListener(FaultEvent.FAULT, onServiceFault);
			TestingService.addEventListener(ResultEvent.RESULT, onServiceComplete);
			TestingService.addEventListener(InvokeEvent.INVOKE, onServiceStart);
			
			TestingService.loadWSDL();
				
        }
        
		private function onServiceStart(event:Event):void{
			
		}
		
		private function onServiceComplete(event:ResultEvent):void{			
			Result = event.result.toString();
//			trace(Result);
			 this.dispatchEvent(new Event(TESTINGEVENT));
		}
			
		private function onServiceFault(event:FaultEvent):void{
			trace(event.fault.toString());		
			Alert.show(event.fault.toString());		
			Result = event.fault.toString();
			initializeFailed();
		}
		
		/**
		 * 第三步,初始化上传
		 * */
		private function onServiceLoadComplete(event:LoadEvent):void{
            //Alert.show("Service load complete!");
            //testingService.testingImp(0, "int test(int a, int b){return a+b;}","");
            //Alert.show(TestingService.endpointURI);
            //读取下一个资源，初始化上传
			var loader:URLLoader = new URLLoader(); 
			var req:URLRequest = new URLRequest(UploadUrl + "?Init");
			loader.addEventListener(IOErrorEvent.IO_ERROR,onInitializError);
			loader.addEventListener(Event.COMPLETE,onInitializeResult); 
			loader.load(req); 
				
        }
		
		protected function onInitializError(event:IOErrorEvent):void{ 
           	Result = ":-( Sorry，系统故障，请与系统管理员联系...";  
           	initializeFailed();         	
           	
        }
        
        protected function initializeFailed():void{	       
	        this.dispatchEvent(new Event(LOADINGEVENT));	
        }
            
		
		protected function onInitializeResult(event:Event):void{                	
            var loader:URLLoader = event.target as URLLoader;
            Session=loader.data;
            trace("初始化完毕，系统Session ID=" + Session );
            this.dispatchEvent(new Event(LOADINGEVENT));
          
	        
        }
        //显示工作进度条
        public function showPop(parent:DisplayObject):void{
        	Pop = PopUpManager.createPopUp(parent,PopWorkingIndicator,true);
        }
		//隐藏工作进度条
		public function clearPop(): void{
			if(Pop!=null){
				PopUpManager.removePopUp(Pop);
				Pop = null;
			}
		}

		public function doTesting():void{
						
			Options = "FileName=" +  this.FileName + "\r\n";
			if(!isAST()){
				if(this.getSourceType() ==JAVA){
					Options += "TargetClass=" + ClassName + "\r\n";
	 				Options += "TargetMethod=" + Method + "\r\n";
				} else {
					Options = "TargetFunctionName=" + Method +"\r\n";
				}
				
				if(condition>-1){
					Options+="ConditionType="+ this.conditionType +"\r\n";
				}
			}
			
			trace(Options + this.TestingType);
			//Alert.show(Options + "\r\n" + this.TestingType);
			Result="";
			TestingService.testingImp(this.TestingType, Code, Options);
			
		}
		

		
		public function getMethodList():void{
			trace("Geting method list");
			if(TestingService==null){
				trace("Service null!");
			}
			if(this.getSourceType()==1){
				TestingService.testingImp(JAVAMETHOD, Code, "");
			} else {
				TestingService.testingImp(CPPFUNCTION, Code, "");
			}
			
		}
		
		public function setTestingMethod(className:String, method:String): void{
			ClassName = className;
			Method = method;
		}

	}
}