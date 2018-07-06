class Matrix{
    constructor(rows, cols){
        if(typeof rows === "object"){
            this.cols = rows[0].length;
            this.rows = rows.length;
            this.data = rows;
        } else {
            this.cols = cols || 1;
            this.rows = rows || 1;
            this.data = [];
            for(let i = 0; i < this.rows; i++){
                this.data[i] = [];
                for(let j = 0; j < this.cols; j++){
                    this.data[i][j] = 0;
                }
            }
        }
        
    }
    static fromArray(arr){
        let m = new Matrix(arr.length, 1);
        for(let i = 0, len = arr.length; i < len; i++){
            m.data[i][0] = arr[i];
        }
        return m;
    }
    toArray(){
        let array = [];
        for(let i = 0; i < this.rows; i++){
            for(let j = 0; j < this.cols; j++){
                array.push(this.data[i][j]);
            }
        }
        return array;
    }
    
    static subtract(a, b){
        let result = new Matrix(a.rows, a.cols);
        for(let i = 0; i < a.rows; i++){
            for(let j = 0; j < a.cols; j++){
                result.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }
        return result;
    }
    
    static multiply(m1, m2){
        if(m1.cols === m2.rows){
            var dotProduct = new Matrix(m1.rows, m2.cols);
            for(let i = 0; i < m1.rows; i++){
                for(let j = 0; j < m2.cols; j++){
                    var sum = 0
                    for(let k = 0; k < m2.rows; k++){
                        sum += m1.data[i][k] * m2.data[k][j];
                    }
                    dotProduct.data[i][j] = sum;
                }
                
            }
            return dotProduct;
        } else {
            throw Error("Columns should match rows of the matrix.")
        }
    }
    
    getMatrix(){
        return this.data;
    }
    
    printTable(){
        console.table(this.getMatrix());
    }
    map(fn){
        let result = new Matrix(this.rows, this.cols);
        for(let i = 0; i < this.rows; i++){
            for(let j = 0; j < this.cols; j++){
                result.data[i][j] = fn(this.data[i][j]);
            }
        }
        return result;
    }
    apply(fn){
        for(let i = 0; i < this.rows; i++){
            for(let j = 0; j < this.cols; j++){
                this.data[i][j] = fn(this.data[i][j]);
            }
        }
    }
    static transpose(matrix){
        let result = new Matrix(matrix.cols, matrix.rows);
        
        for(let i = 0; i < matrix.rows; i++){
            for(let j = 0; j < matrix.cols; j++){
                result.data[j][i] = matrix.data[i][j];
            }
        }
        return result;
    }
    
    add(n){
        if(typeof n !== 'number' && n.getMatrix){
            n = n.getMatrix();
        }
        for(let i = 0; i < this.rows; i++){
            for(let j = 0; j < this.cols; j++){
                if(typeof n === 'number'){
                    this.data[i][j] += n;
                } else {
                    this.data[i][j] += n[i][j];
                }
            }
        }
    }
    
    multiply(n){
        if(typeof n !== 'number' && n.getMatrix){
            n = n.getMatrix();
        }
        for(let i = 0; i < this.rows; i++){
            for(let j = 0; j < this.cols; j++){
                if(typeof n === 'number'){
                    this.data[i][j] *= n;
                } else {
                    this.data[i][j] *= n[i][j];
                }
            }
        }
    }
    
    randomize(n){
        for(let i = 0; i < this.rows; i++){
            for(let j = 0; j < this.cols; j++){
                this.data[i][j] = Math.random() * 2 - 1;
            }
        }
    }
}
export default Matrix;