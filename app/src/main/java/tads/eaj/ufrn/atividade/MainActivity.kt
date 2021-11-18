package tads.eaj.ufrn.atividade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.atividade.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        when(it.resultCode){
            1 -> {
                val params = it.data?.extras
                binding.valorPeso.text = params?.getString("resposta")
            }
            2 ->{
                val params = it.data?.extras
                binding.valorAltura.text = params?.getString("resposta")
            }
        }
    }
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        var altAlturaBtn = binding.btnAlterarAltura;
        var altPesoBtn = binding.btnAlterarPeso;
        var calcularBtn = binding.btnCalcular;

        altAlturaBtn.setOnClickListener {
            var alturaTitulo = binding.txtAltura;
            val intent = Intent(this, TelaAlterar::class.java)
            val param = Bundle()
            param.putString("conteudo", alturaTitulo.text.toString())
            intent.putExtras(param)
            launcher.launch(intent)
        }
        altPesoBtn.setOnClickListener {
            var pesoTitulo = binding.txtPeso;
            val intent = Intent(this, TelaAlterar::class.java)
            val param = Bundle()
            param.putString("conteudo", pesoTitulo.text.toString())
            intent.putExtras(param)
            launcher.launch(intent)
        }
        calcularBtn.setOnClickListener{
            var valorAltura = binding.valorAltura.text.toString().toFloat()
            var valorPeso = binding.valorPeso.text.toString().toFloat()
            var imc = valorPeso / (valorAltura*valorAltura)
            binding.txtLegenda.text = String.format("%.2f", imc)
            var resultado = "Você está em situação de "
            if (imc < 16) resultado += "MAGREZA EXTREMA! Procure um médico!"
            else if (imc <17) resultado += "MAGREZA MODERADA! Atenção!"
            else if (imc < 18.5) resultado += "MAGREZA LEVE!"
            else if (imc <25) resultado += "uma pessoa SAUDÁVEL! Ótimo!!"
            else if (imc < 30) resultado += "SOBREPESO! "
            else if (imc < 35) resultado += "OBESIDADE I! Atenção!"
            else if (imc < 40) resultado += "OBESIDADE II! Cuidado!"
            else               resultado += "OBESIDADE III! Procure um médico!"
            binding.txtResultado.text = resultado
        }
    }

    override fun onStart() {
        super.onStart()
        val preferences = getSharedPreferences("prefs", MODE_PRIVATE)
        binding.valorPeso.text = preferences.getString("peso", "0.00")
        binding.valorAltura.text = preferences.getString("altura", "0.00")
        binding.txtResultado.text = preferences.getString("resultado", "Aperte o botão calcular!")
    }

    override fun onStop() {
        super.onStop()
        val preferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var edit = preferences.edit()
        edit.putString("peso", binding.valorPeso.text.toString())
        edit.putString("altura", binding.valorAltura.text.toString())
        edit.putString("resultado", binding.txtResultado.text.toString())
        edit.apply()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.valorPeso.text = savedInstanceState.getString("peso")
        binding.valorAltura.text = savedInstanceState.getString("altura")
        binding.txtResultado.text = savedInstanceState.getString("resultado")

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("peso", binding.valorPeso.text.toString())
        outState.putString("altura", binding.valorAltura.text.toString())
        outState.putString("resultado", binding.txtResultado.text.toString())
    }

}