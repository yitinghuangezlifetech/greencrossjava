/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	config.toolbarGroups = [
{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
{ name: 'paragraph', groups: [ 'align', 'blocks', 'list', 'indent', 'bidi', 'paragraph' ] },
{ name: 'clipboard', groups: [ 'undo', 'clipboard' ] },
{ name: 'forms', groups: [ 'forms' ] },
{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
{ name: 'links', groups: [ 'links' ] },
{ name: 'insert', groups: [ 'insert' ] },
{ name: 'styles', groups: [ 'styles' ] },
{ name: 'colors', groups: [ 'colors'] },
{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing','Youtube' ] },
{ name: 'tools', groups: [ 'tools' ] }
	                        
	                        ];
	config.removeButtons = 'Iframe,Styles,Blockquote,BidiLtr,CreateDiv,Outdent,NumberedList,BulletedList,Indent,Language,BidiRtl,Cut,Copy,Paste,PasteText,PasteFromWord,About,Maximize,ShowBlocks,HorizontalRule,Flash,Smiley,PageBreak,Link,Unlink,Anchor,Subscript,Superscript,Strike,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Scayt,Save,NewPage,Preview,Print,Templates,SpecialChar,SelectAll,Find,Replace';

	config.language = 'zh-tw';//中文
	config.image_Link = ' ';//把圖片上傳預覽的拿掉
	config.filebrowserUploadUrl = "mswp/WatEcSup!execute";//指定 action and function 可以寫在 頁面上，也可以寫在這
	config.filebrowserImageBrowseLinkUrl = "mswp/WatEcSup!execute";//指定 action and function 可以寫在 頁面上，也可以寫在這
	config.extraPlugins = 'youtube';//使用youtube套件
	config.removePlugins = 'iframe';
/*	config.enterMode = CKEDITOR.ENTER_BR;
	config.shiftEnterMode = CKEDITOR.ENTER_P;*/            
	config.removeDialogTabs = 'image:advanced';
	config.youtube_width = '650';
	config.youtube_older = false;
	config.youtube_privacy = false;
};
