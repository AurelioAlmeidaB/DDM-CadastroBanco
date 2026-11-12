package br.edu.ifsp.ddm6banco

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.ddm6banco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //VAMOS CRIAR AS REFERÊNCIAS PARA CRIAÇÃO DO
        //BANCO DE DADOS E DA TABELA
        banco= SQLiteDatabase.openOrCreateDatabase(
            this.getDatabasePath("banco.db"),
            null
        )

        //AQUI CRIAMOS A TABELA SE ELA NÃO EXISTIR
        banco.execSQL( "CREATE TABLE IF NOT EXISTS "+
                "cadastro( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nome TEXT, telefone TEXT)"
        )

        binding.btnIncluir.setOnClickListener {
            incluir()
        }

        binding.btnListar.setOnClickListener {
            listar()
        }

        binding.btnAlterar.setOnClickListener {
            atualizar()
        }

        binding.btnPesquisar.setOnClickListener {
            pesquisar()
        }

         binding.btnExcluir.setOnClickListener {
            excluir()
        }
    }

    private fun pesquisar(){
        val registro=banco.query(
            TABLE_NAME,
            null,
            "_id = ${binding.edtCod.text.toString()}",
            null,
            null,
            null,
            null
        )

        //vamos verificar se encontrou ou nao
        if(registro.moveToNext()){
            binding.edtNome.setText(
                registro.getString(NOME))
            binding.edtFone.setText(
                registro.getString(TELEFONE)
            )
        }
        else{
            Toast.makeText(
                this,
                "Registro não encontrado",
                Toast.LENGTH_LONG
            ).show()        }
    }




    private fun atualizar() {
        val registro= ContentValues()
        registro.put("nome",binding.edtNome.text.toString())
        registro.put("telefone",binding.edtFone.text.toString())

        banco.update(
            "cadastro",
            registro,
            "_id = ${binding.edtCod.text.toString()}",
             null
        )

        Toast.makeText(
            this,
            "Alterado com sucesso!",
            Toast.LENGTH_LONG
        ).show()

    }

    private fun listar() {
        val registro = banco.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val saida = StringBuilder()

        while(registro.moveToNext()){
            saida.append(registro.getString(NOME))
            saida.append("\n")
        }

        Toast.makeText(
            this,
            saida.toString(),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun incluir(){
        /*
        A LINHA 'VAL REGISTRO' CRIA UM
        OBJETO D0 TIPO ContentValues QUE
        FUNCIONA COMO UM PACOTE OU DICIONARIO
        DE CHAVE E VALOR (NOME_COLUA--> VALOR)
        ELA É USADA PARA PREPARAR OS DDOS ANTES
        DE INSERIR OU ATUALIZAR O BANCO
         */
        val registro = ContentValues()
        registro.put("nome",binding.edtNome.text.toString())
        registro.put("telefone",binding.edtFone.text.toString())

        banco.insert("cadastro",
            null, registro)

        Toast.makeText(
            this,
            "Inclusão realizada com sucesso!",
            Toast.LENGTH_LONG
        ).show()
        limpar()

    }

        private fun limpar() {
        binding.edtCod.setText.clear()
        binding.edtNome.setText.clear()
        binding.edtFone.setText.clear()
    }

    private fun excluir() {
        banco.delete(
            tableName = "cadastro",
            whereClause = "_id = ${binding.edtCod.text.toString()}",
            whereArgs = null
        )
        Toast.makeText(
            context = this,
            text = "Exclusão realizada com sucesso!",
            duration = Toast.LENGTH_LONG
        ).show()
        limpar()
    }
    
    //VAMOS CRIAR O COMPANION OBJECT
    //QUE REUNE CONSTANTES DA CLASSE
    companion object {
        private const val DB_NAME="banco.db"
        private const val TABLE_NAME="cadastro"
        //os valores numéricos irão identificar
        //os 'índices' das colunas das
        //tabelas que será criada
        private const val ID=0
        private const val NOME=1
        private const val TELEFONE=2
    }

}