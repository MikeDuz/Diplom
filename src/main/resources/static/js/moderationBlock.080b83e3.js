(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["moderationBlock"],{2007:function(t,e,i){"use strict";i("c201")},"32d2":function(t,e,i){"use strict";i.r(e);var o=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"ModerationBlock",class:t.className},[i("div",{staticClass:"ModerationBlock-Section"},[i("router-link",{staticClass:"ModerationBlock-Link",attrs:{to:{name:"edit",params:{id:""+t.id}}}},[t._v(" Редактировать ")])],1),t.myPosts?t._e():i("div",{staticClass:"ModerationBlock-Section"},["declined"!==t.param?i("div",{staticClass:"ModerationBlock-Link",on:{click:t.onDeclne}},[t._v(" Отклонить ")]):t._e(),"accepted"!==t.param?i("div",{staticClass:"ModerationBlock-Link",on:{click:t.onAccept}},[t._v(" Утвердить ")]):t._e()])])},a=[],c=(i("a9e3"),{props:{className:{type:String,required:!1},id:{type:Number,required:!0},myPosts:{type:Boolean,required:!1,default:!1}},computed:{param:function(){return this.$route.params.pathMatch}},methods:{onDeclne:function(){this.$emit("moderated","decline")},onAccept:function(){this.$emit("moderated","accept")}}}),n=c,s=(i("2007"),i("2877")),r=Object(s["a"])(n,o,a,!1,null,null,null);e["default"]=r.exports},c201:function(t,e,i){}}]);
//# sourceMappingURL=moderationBlock.080b83e3.js.map