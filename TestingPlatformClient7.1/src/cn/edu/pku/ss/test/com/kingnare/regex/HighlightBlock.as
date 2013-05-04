/*
///////////////////////////////////////////////////////////////////////////////
//
//  HighlightBlock by Kingnare.com.
//	All Rights Reserved.
//
//  Free to use for any non-commercial purposes just list me in your credits. 
//	Contact me at auzn1982[at]gmail.com concerning commercial use.  
//	
//	The above copyright notice and this permission notice shall be included in all
//	copies or substantial portions of the Software.
//
///////////////////////////////////////////////////////////////////////////////
*/

package cn.edu.pku.ss.test.com.kingnare.regex
{
	import flash.display.Shape;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.text.TextLineMetrics;
	
	import mx.core.IUITextField;
	import mx.flash.UIMovieClip;
	import mx.controls.Alert;
	
	/**
	 * 
	 * 根据IUITextField文本的起始和结束字符索引绘制图形并显示在文本选择区。
	 * <br/><br/>
	 * E-mail:auzn1982[at]gmail.com
	 * @author auzn at Kingnare.com
	 * 
	 */	
	
	public class HighlightBlock extends UIMovieClip
	{
		/**
		 * 目标文本
		 */		
		private var _textField:IUITextField;
		/**
		 * 偏移点，调整绘图Shape与目标文本的位置差距
		 */		
		private var _offsetPoint:Point;
		
		
		/**
		 * 
		 * HighlightBlock 构造方法
		 * @param textField 目标文本
		 * 
		 */		
		public function HighlightBlock(textField:IUITextField)
		{
			super();
			_offsetPoint = new Point(0, 0);
			_textField = textField;
		}
		
		/**
		 * 
		 * 绘制高亮块
		 * 根据起始字符索引及结束字符索引获得有效的绘制范围
		 * 如果绘制范围合法，将绘制高亮块。
		 * 
		 * @param beginIndex 目标文本中起始字符索引
		 * @param endIndex 目标文本中结束字符索引
		 * 
		 * @see #getValidBeginCharIndex()
		 * @see #getValidEndCharIndex()
		 */		
		public function highLightDraw(beginIndex:Number, endIndex:Number):void
		{
			var beginValidIndex:Number = getValidBeginCharIndex(beginIndex);
//			Alert.show(beginValidIndex.toString());
			var endValidIndex:Number = getValidEndCharIndex(endIndex);
			if(beginValidIndex == -1 || endValidIndex == -2)
			{
				//throw new Error("Invalid value");
				return;
			}
			if(beginValidIndex<=endValidIndex)
			{
				normalDraw(beginValidIndex, endValidIndex);
			}
		}
		
		/**
		 * 
		 * 根据起始字符索引及结束字符索引绘制，单行/多行。
		 * 
		 * @param beginIndex 目标文本中起始字符索引
		 * @param endIndex 目标文本中结束字符索引
		 * 
		 * @see #drawSingleLine()
		 */		
		
		private function normalDraw(beginIndex:Number, endIndex:Number):void
		{
			//trace("Normal Draw");
			var beginLineIndex:Number = _textField.getLineIndexOfChar(beginIndex);
			var endLineIndex:Number = _textField.getLineIndexOfChar(endIndex);
			var disLineNum:Number = endLineIndex-beginLineIndex;
			
			Alert.show("beginLineIndex:"+beginLineIndex+" endLineIndex:"+endLineIndex+" disLineNum"+disLineNum);
			Alert.show("line0:"+_textField.getLineLength(0)+" line1:"+_textField.getLineLength(1)+" line2:"+_textField.getLineLength(2)+" line3:"+_textField.getLineLength(3));
			//1行
			if(disLineNum<1)
			{
				drawSingleLine(beginIndex, endIndex);
				return;
			}
			//大于或等于一行
			//首行
			drawSingleLine(beginIndex, _textField.getLineOffset(beginLineIndex)+_textField.getLineLength(beginLineIndex)-2);
			//中间行
			for(var i:Number=beginLineIndex+1;i<endLineIndex;i++)
			{
				if(_textField.getLineLength(i)>2){
				drawSingleLine(_textField.getLineOffset(i), _textField.getLineOffset(i)+_textField.getLineLength(i)-2);
				}
			}
			//尾行
			drawSingleLine(_textField.getLineOffset(endLineIndex), endIndex);
		}
		
		/**
		 * 
		 * 根据起始字符索引及结束字符索引绘制，单行。
		 * 
		 * @param beginIndex 目标文本中起始字符索引
		 * @param endIndex 目标文本中结束字符索引
		 * 
		 * @throws Error  起始和结束索引跨多个行。 
		 */		
		private function drawSingleLine(beginIndex:Number, endIndex:Number):void
		{
			var beginLineIndex:Number = _textField.getLineIndexOfChar(beginIndex);
			var endLineIndex:Number = _textField.getLineIndexOfChar(endIndex);
			var disLineNum:Number = endLineIndex-beginLineIndex;
			//1行
			if(disLineNum<1)
			{
				var frame:Rectangle = _textField.getCharBoundaries(beginIndex);
				
//				Alert.show(frame.x.toString()+" "+frame.y.toString()+" "+frame.width.toString()+" "+frame.height.toString());
				
				frame.y = getDisLineHeightByLine(beginLineIndex);
				frame.width = _textField.getCharBoundaries(endIndex).x - _textField.getCharBoundaries(beginIndex).x+_textField.getCharBoundaries(endIndex).width;
				showBlock(frame);
			}
			else
			{
				throw new Error("drawSingleLine:disLineNum >= 1.");
			}
		}
		
		/**
		 * 
		 * 通过行索引查找相对显示行的总高度
		 * @param lineIndex 行索引
		 * @return 相对高度
		 * 
		 */		
		public function getDisLineHeightByLine(lineIndex:Number):Number
		{
			
			var addHeight:Number = 2;
			for(var i:Number=_textField.scrollV-1;i<lineIndex;i++)
			{
				var showLine:TextLineMetrics = _textField.getLineMetrics(i);
				addHeight += showLine.height;
			}
			return addHeight;
		}
		
		/**
		 * 
		 * 通过字符索引查找相对显示行的总高度
		 * @param charIndex 字符索引
		 * @return 相对高度
		 * 
		 */		
		public function getDisLineHeightByChar(charIndex:Number):Number
		{
			var line:Number = _textField.getLineIndexOfChar(charIndex);
			return getDisLineHeightByLine(line);
		}
		
		/**
		 * 
		 * 获取当前有效的起始字符索引
		 * @param beginIndex 起始字符索引
		 * @return 有效的索引值。若索引值超出文本范围，返回-1
		 * 
		 */		
		public function getValidBeginCharIndex(beginIndex:Number):Number
		{
			var len:Number = _textField.text.length;
			if(beginIndex<0 || beginIndex>len-1) 
			{
				return -1;
			}
			var line:Number = _textField.getLineIndexOfChar(beginIndex);
			
			if(line<_textField.scrollV-1)
			{
				textField.scrollV = line;
//				line = _textField.scrollV-1;
//				return _textField.getLineOffset(line);
			}
			return beginIndex;
		}
		
		
		/**
		 * 
		 * 获取当前有效的结束字符索引
		 * @param endIndex 结束字符索引
		 * @return 有效的索引值。若索引值超出文本范围，返回-2
		 * 
		 */		
		public function getValidEndCharIndex(endIndex:Number):Number
		{
			var len:Number = _textField.text.length;
			if(endIndex<0 || endIndex>len-1) 
			{
				return -2;
			}
			var line:Number = _textField.getLineIndexOfChar(endIndex);
			if(line>_textField.bottomScrollV-1)
			{
				textField.scrollV = line;
//				line = _textField.bottomScrollV-1;
//				return _textField.getLineOffset(line)+_textField.getLineLength(line)-1;
			}
			return endIndex;
		}
		
		
		/**
		 * 
		 * 绘制并加到显示容器中
		 * @param pos 
		 * 
		 */		
		private function showBlock(pos:Rectangle):void
		{
			var rect:Rectangle = new Rectangle(_offsetPoint.x+pos.x, _offsetPoint.y+pos.y, pos.width, pos.height);
			this.addChild(drawBlock(rect));
		}
		
		
		/**
		 * 
		 * 绘制
		 * @param rect
		 * @return 绘制图形
		 * 
		 */		
		protected function drawBlock(rect:Rectangle):Shape
		{
			var block:Shape = new Shape();
			block.graphics.clear();
			block.graphics.beginFill(0x0099CC, .35); 
			block.graphics.lineStyle(1, 0x0099CC, .65, true);
			block.graphics.drawRoundRectComplex(rect.x, rect.y, rect.width, rect.height, 0, 0, 0, 0);
			block.graphics.endFill();
			return block;
		}
		
		/**
		 * 
		 * 目标文本组件
		 * @param tf 目标文本
		 * 
		 */		
		public function set textField(tf:IUITextField):void
		{
			_textField = tf;
		}
		
		/**
		 * 
		 * @private
		 * @return 目标文本
		 * 
		 */		
		public function get textField():IUITextField
		{
			return _textField;
		}
		
		/**
		 * 
		 * 偏移点
		 * @param op 偏移点
		 * 
		 */		
		public function set offsetPoint(op:Point):void
		{
			_offsetPoint = op;
		}
		
		/**
		 * 
		 * @private
		 * @return 偏移点
		 * 
		 */		
		public function get offsetPoint():Point
		{
			return _offsetPoint;
		}
	}
}