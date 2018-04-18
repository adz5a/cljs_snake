"use strict";

// DOC:
// http://www.ecma-international.org/ecma-262/6.0/#sec-ecmascript-language-statements-and-declarations
// block statement
{
  console.log("this is valid");
};


if (true) {
  console.log("the next statement is execuded");
  console.log("here this is a block");
};

if (true) console.log("but it can be inlined");

switch ("yolo") {

  case "swag": 
    console.log("this statement is run");

  case "yolo":{
    console.log("but it can be a block statement");
  }

}
