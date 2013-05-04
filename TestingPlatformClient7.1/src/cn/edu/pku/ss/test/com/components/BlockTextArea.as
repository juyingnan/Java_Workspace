 package cn.edu.pku.ss.test.com.components
{
	 
	 import cn.edu.pku.ss.test.com.kingnare.regex.HighlightBlock;
	 
	 import flash.display.DisplayObject;
	 import flash.geom.Point;
	 
	 import mx.controls.Alert;
	 import mx.controls.TextArea;
	 import mx.core.IUITextField;
		 
	 public class BlockTextArea extends TextArea
		 {
		 private var blockArray:Array;
		 
		 private var textFields:IUITextField;
		  
		 public function BlockTextArea()
		 {
			 super();
			 blockArray = [];
			 }
		 
		 public function addLineInfo():void
		 {
			 textFields = this.textField;
			 var currLineBeginIndex:int;
			 var currLineText:String;
			 var newLineText:String;
			 var lineLength:int = textFields.numLines;
			 var num:int;
			 for(var i:int=0;i<lineLength;i++)
			 {
				 
					 num++;
					 
					 if(num%2==1)
					 {
						 currLineBeginIndex = textFields.getLineOffset(i);
						 currLineText = textFields.getLineText(i);
						 
						 var n:int = num/2+1;
						 
						 newLineText = n+": "+currLineText;
						 textFields.replaceText(currLineBeginIndex,currLineBeginIndex+textFields.getLineLength(i),newLineText);
					 }
				 
			 }

		 }
		  
		 public function caculateBeginEndIndex(beginLine:int, beginColumn:int, endLine:int, endColumn:int):Array
		 {
			 textFields = this.textField;
			 var beginIndex:int = textFields.getLineOffset(beginLine-1)+(beginColumn-1);
			 var endIndex:int = textFields.getLineOffset(endLine-1)+endColumn;
			 return [beginIndex,endIndex];
		 }
		 
		 public function scrollBlock(beginIndex:int, endIndex:int):void
		 {
//			 Alert.show(beginIndex+" "+endIndex);
//			 var movieTip:HighlightBlock= new HighlightBlock(this.textField);
//			  
//			 movieTip.offsetPoint = new Point(0, 0);
//			 movieTip.highLightDraw(beginIndex, endIndex);
//			 movieTip.toolTip = "beginIndex: "+beginIndex+"\nendIndex: "+endIndex+"\nlength: "+(endIndex-beginIndex+1).toString()+"\ntext:\t"+this.textField.text.substring(beginIndex, endIndex+1);
//			 clearBlock();
//			this.addChild(movieTip);
//			 blockArray.push(movieTip);
			 
			 textFields = this.textField;
			 var locateLine:int = textField.getLineIndexOfChar(beginIndex);
			 textFields.scrollV = locateLine;
		 }
		  
		 public function clearBlock():void
		{
			 var len:uint = blockArray.length;
			  
			 for(var k:uint=0;k<len;k++)
				 {
					 var obj:DisplayObject = blockArray[k]
					this.removeChild(obj);
					 obj = null;
					 }
			 blockArray = [];
			}
		}
	}