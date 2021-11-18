package tads.eaj.ufrn.atividade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.atividade.databinding.ActivityMainBinding
import tads.eaj.ufrn.atividade.databinding.ActivityTelaAlterarBinding

class TelaAlterar : AppCompatActivity() {

    private lateinit var binding: ActivityTelaAlterarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tela_alterar)
        var param = intent.extras

        var text =  param?.getString("conteudo")
        binding.txtX.text = text
        var alterarBtn = binding.btnAlterar
        var cancelarBtn = binding.btnCancelar
        var valorInput = binding.inputUsuario
        alterarBtn.setOnClickListener{
            var intent = Intent()
            var param = Bundle()
            param.putString("resposta", valorInput.text.toString())
            intent.putExtras(param)
            if(text.equals("Peso (kg):")){
                setResult(1, intent)
            }
            else{
                setResult(2, intent)
            }
            finish()
        }
        cancelarBtn.setOnClickListener{
            finish()
        }
    }
}