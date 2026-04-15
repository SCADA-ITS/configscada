INSERT INTO conf.ims_incident_type_task_values (task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
--SendToTelegramNotification tiene tres parámetros:                                                                                                             
--1 = TOKEN BOT que usamos para la notifiación                                                                                                                  
--2 = CHAT_ID del canal donde vamos a mandar la incidencia                                                                                                      
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación                                                                                             
(10, 1, 01010409, 'Inicio de evento de atletismo: Carrera                                                                                                       
@alias                                                                                                                                                          
@tipo                                                                                                                                                           
@localizacion                                                                                                                                                   
@afeccion                                                                                                                                                       
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),                                                                                                     
                                                                                                                                                                
--SendToTelegramNotification tiene tres parámetros:                                                                                                             
--1 = TOKEN BOT que usamos para la notifiación                                                                                                                  
--2 = CHAT_ID del canal donde vamos a mandar la incidencia                                                                                                      
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación                                                                                             
(10, 1, 02010409, '@alias                                                                                                                                       
@tipo                                                                                                                                                           
@localizacion                                                                                                                                                   
@afeccion                                                                                                                                                       
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),                                                                                                     
                                                                                                                                                                
--SendMailTask tiene tres parámetros                                                                                                                            
--1 addressTO = Destinatarios                                                                                                                                   
--2 addressCC = Destinatarios en copia                                                                                                                          
--3 addressBCC = Destinatarios en copia oculta                                                                                                                  
--4 subject = Asunto del correo electrónico                                                                                                                     
--5 body = Cuerpo del mensaje a enviar                                                                                                                          
(8, 1, 03010409, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),                         
(8, 4, 03010409, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		                                                                
(8, 5, 03010409, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);                                              
                                                                                                                                                                
                                                                                                                                                                
                                                                                                                                                                
                                                                                                                                                                
                                                                                                                                                                