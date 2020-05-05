# IoT Connector
## Sobre
Aplicativo android para comunicação com dispositivos via protocolo MQTT

App desenvolvido para trabalho de conclusão de curso tendo um de seus objetivos a contribuir para a comunidade com o código fonte. Atualmente o aplicativo tem validação por usuário, dados salvos no Realtime Database e realiza publicações via MQTT utilizando a biblioteca do Eclipse Paho.

Tecnologias utilizadas:
- Conexão MQTT: Eclipse Paho
- Autenticação e Banco de dados: Google Firebase


## Instruções de uso
### Atualização do Status
Cada dispositivo é tratado por um tópico diferente.
Quando está com a tela do dispositivo aberta o app realiza um <i>subscribe</i> no tópico "status_" + <i>tópico do dispositivo</i>, por exemplo:
* Host: localhost
* Port: 1883
* Topic: termometro

Ao abrir a tela do dispositivo o app estará <i>subscribed</i> no tópico "status_termometro" recebendo as mensagens e salvando na base de dados.
