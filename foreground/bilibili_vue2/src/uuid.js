import { v4 as uuidv4 } from 'uuid';
uuidv4.install = function (Vue) {
    Vue.prototype.$uuidv4 = uuidv4 ;
}
export default uuidv4;