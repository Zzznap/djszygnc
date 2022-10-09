// 1. 创建事件中心
const eventBus = new Vue();
// 将创建出来的空的vue实例挂载Vue构造函数的原型上
Vue.prototype.eventBus = eventBus;
 