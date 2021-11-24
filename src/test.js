function foo(p1, p2, ...p3) {
  return function bar(p4) {
    console.log('p1 :>> ', p1);
    console.log('p2 :>> ', p2);
    console.log('typeof p1 :>> ', typeof p1);
    console.log('typeof p2 :>> ', typeof p2);

    console.log('p3 :>> ', p3);
    console.log('p4 :>> ', p4);
    console.log('typeof p3 :>> ', Array.isArray(p3));
    console.log('typeof p4 :>> ', typeof p4);
  };
}

foo(1, undefined, null, null, undefined)(5, 6);

const a = [];
const b = [];

const f = a.some((value, index, array) => b[index] === value);
console.log('f :>> ', f);
