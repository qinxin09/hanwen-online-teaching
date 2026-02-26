function f(item){
    item.item = 5;
}
let a={
    item:2
}
f({...a})
console.log(a)
