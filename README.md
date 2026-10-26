# Nu Demo — projeto Android

Aplicativo independente de demonstração, com visual próprio inspirado em carteiras digitais. Não é o Nubank oficial, não conecta a bancos e só exibe valores fictícios.

## Recursos
- Saldo de demonstração editável
- Atalhos para adicionar/remover R$ 50
- Movimentações fictícias
- Interface em português

## Compilação
Requer JDK 17, Android SDK Platform 35 e Gradle compatível com Android Gradle Plugin 8.7.3. Em um ambiente já configurado, na raiz do projeto:

```bash
gradle assembleDebug
```

APK esperado: `app/build/outputs/apk/debug/app-debug.apk`.

No Termux, preparar o SDK Android pode ser trabalhoso; este ZIP contém o código-fonte completo, não um APK pré-compilado.
