from selenium import  webdriver
import time
import pymysql

conn_detail = pymysql.connect(host ='xxxx' , unix_socket='/tmp/mysql.sock', user='xxxx', passwd='xxxxx' , db='mysql' )

cur = conn_detail.cursor()

cur.execute("USE pfmdatabase")


driver = webdriver.PhantomJS(executable_path=r"/home/vidit/Desktop/phantomjs-2.1.1-linux-x86_64/bin/phantomjs")

url =" http://economictimes.indiatimes.com/tata-steel-ltd/stocks/companyid-12902.cms"
driver.get(url)
while True :
    time.sleep(12)

    try :

        stock_exchange ="NSE"
        share_symbol = "NSE: TATASTEEL"
                        #driver.find_element_by_xpath(".//*[@id='netspidersosh']/section[5]/section[2]/section[1]/section[2]/div[1]/ul/li[1]").text
        print("Stock Exchange :"  , stock_exchange)

        share_name = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[1]/h1").text
      #  print(stock_exchange +" DATA  OF : "+ share_name )
        print("\n")
        print("Share Name : " , share_name , type(share_name))

        print("Share Symbol : " , share_symbol , type(share_symbol))

        share_price = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/section[1]/section[1]/div[1]").text
        #share_price = float(share_price)
        print("Share Price : " , share_price , type(share_price))

        price_time= driver.find_element_by_xpath(".//*[@class='leftData']/ul/li/div/span[2]").text
        print("Price Time : " ,  price_time , type(price_time))


        net_change = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/section[1]/section[1]/div[2]/p[2]/span[2]").text
        #net_change = float(net_change)
        print("NSE Net Change :"  ,  net_change , type(net_change))

        nse_percent_change = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/section[1]/section[1]/div[2]/p[2]/span[3]").text
        nse_percent_change = nse_percent_change.strip('(').strip(')').strip('%')
        #nse_percent_change = float(nse_percent_change)
        #print(type(nse_percent_change))

        print("NSE Percent Change :" , nse_percent_change , type(nse_percent_change))

        volume = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/section[1]/section[1]/div[3]/p").text
        volume = volume.replace(',' , '')
        #volume = int(volume)
        print('Volume :' , volume  ,  type(volume))

        open_price = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/section[1]/section[1]/div[4]/p[2]").text
        #open_price = float(open_price)
        print('open price :' , open_price  , type(open_price))

        prev_close_price = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/section[1]/section[1]/div[5]/p[2]").text
        #prev_close_price = float(prev_close_price)
        print('Previous close Price :'  , prev_close_price , type(prev_close_price))

        bid = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/div[2]/div[2]/ul/li[1]/span[2]").text
        print('Bid :' , bid , type(bid))

        market_cap = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/div[2]/div[2]/ul/li[2]/span[2]").text
        #print('Market Caps :' + market_cap)

        pe = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/div[2]/div[2]/ul/li[3]/span[2]").text
        #print('(P/e) :' + pe)

        div_yield = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/div[2]/div[2]/ul/li[5]/span[2]").text
        #print('Dividend Yield :' + div_yield)

        face_value = driver.find_element_by_xpath(".//*[@id='mainContainer']/section[2]/section[1]/section[3]/div[2]/div[3]/ul/li[2]/span[2]").text
        #print('face value :' + face_value)


        live = driver.find_element_by_xpath(".//*[@class='leftData']/ul/li/div/span[1]").text
        print("status" , live)
        #market_cap = float(market_cap)
        #pe = float(pe)
        #div_yield = float(div_yield)
        #face_value = int(face_value)
        print('Market Caps :' , market_cap , type(market_cap))
        print('(P/e) :' , pe , type(pe))
        print('Dividend Yield :' , div_yield , type(div_yield))
        print('face value :' , face_value , type(face_value))

        cur.execute("INSERT INTO Nse (Share_Name , Share_Symbol , Share_Price , Price_Time ,Net_Change ,Percent_Change , Volume , Open_Price , Previous_Close_Price , Bid , Market_Capital , PE_Ratio , Divident_Yeild , Face_Value , status) "
                             "VALUES (%s,           %s,             %s,             %s,         %s,     %s,                 %s,     %s,         %s,                     %s,     %s,             %s,         %s,             %s,%s) " ,
                    (share_name , share_symbol , (share_price) , price_time ,
                                                                                      (net_change) , (nse_percent_change) , (volume) , (open_price) ,
                                                                                      (prev_close_price) ,
                                                                                     bid , (market_cap) , (pe) , (div_yield) , (face_value)  , live) )
        cur.connection.commit()





    except Exception as e :
        print("Error : " , e)

cur.close()
conn.close()
