import conn from "database.js";
import jwt from "jsonwebtoken";

export async function login(req, res){
    const rows = await conn.query(
        `SELECT * FROM users WHERE username = '${req.body.username}'`
    );
    console.log(rows);
    if(rows.length>0){
        if(req.body.password === rows[0].password){
            const token = jwt.sign(rows[0],"rahasia");
            res.send(token);
        }else{
            res.status(401).send("Kata sandi salah");
        }
    }else{
        res.status(401).send("e-mail tidak ditemukan");
    }
}

export default login;
