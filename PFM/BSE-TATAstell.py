from selenium import  webdriver
import time
import pymysql

conn_detail = pymysql.connect(host ='xxxx' , unix_socket='/tmp/mysql.sock', user='root', passwd='xxxx' , db='mysql' )

cur2 = conn_detail.cursor()

cur2.execute("USE pfmdatabase")


driver = webdriver.PhantomJS(executable_path=r"/home/vidit/Desktop/phantomjs-2.1.1-linux-x86_64/bin/phantomjs")

url_2 = "http://www.moneycontrol.com/india/stockpricequote/steel-large/tatasteel/TIS"


while True :

    #time.sleep(20)
    driver.get(url_2)

    try :

        stock_exchange_bse =driver.find_element_by_xpath(".//*[@class='FR PR3']/span").text
        print("Stock Exchange :" + stock_exchange_bse)

        share_name_bse = driver.find_element_by_xpath(".//*[@class='b_42 PT5 PR']/h1").text
        print("Share Name :" + share_name_bse)

        share_rep =driver.find_element_by_xpath(".//*[@class='PB10']/div").text
        #print(share_rep)

        a,b ,c,d , e= share_rep.split("|" , 4)
        print(a)

        price_bse =driver.find_element_by_xpath(".//*[@id='Bse_Prc_tick_div']/span").text
        print(price_bse)

        price_time_bse =driver.find_element_by_xpath(".//*[@class='FL gL_10 bseArr PT4 w70']/div[3]").text
        print(price_time_bse)

        price_change = driver.find_element_by_xpath(".//*[@id='b_changetext']/span").text
        print(price_change)

        percent_change_bse = driver.find_element_by_xpath(".//*[@id='b_changetext']").text

        z ,percent_change_bse = percent_change_bse.split(" " , 2)
        print(percent_change_bse)



        volume_bse = driver.find_element_by_xpath(".//*[@class='FL gL_10 PL10 PR PT15']/span/span[2]").text
        print("Volume : "+ volume_bse)

        prev_close_bse = driver.find_element_by_xpath(".//*[@class='brdb PA5']/div[1]/div[2]").text
        print("Previoud Close :" + prev_close_bse)

        open_bse = driver.find_element_by_xpath(".//*[@class='brdb PA5']/div[2]/div[2]").text
        print("Open Proce :" + open_bse)

        bid_price_bse = driver.find_element_by_xpath(".//*[@class='brdb PA5']/div[3]/div[2]").text
        print("Bid Price Quantity :" + bid_price_bse)

        offer_price_bse = driver.find_element_by_xpath(".//*[@class='brdb PA5']/div[4]/div[2]").text
        print("Offer Price :" + offer_price_bse)

        volume_bse = volume_bse.replace(',' , '')
        percent_change_bse = percent_change_bse.strip('(').strip(')').strip('%')

        cur2.execute("INSERT INTO Bse (Share_Name , Share_Symbol , Share_Price , Price_Time ,Net_Change ,Percent_Change , Volume , Open_Price , Previous_Close_Price , Bid , Offer_Price) "
                             "VALUES (%s, %s,%s , %s , %s , %s , %s , %s , %s , %s , %s) " ,
                    (share_name_bse , a , float(price_bse) , price_time_bse ,
                                                                                      float(price_change) , (percent_change_bse) , (volume_bse) , float(open_bse) ,
                                                                                      float(prev_close_bse) ,
                                                                                     bid_price_bse , (offer_price_bse)  ) )
        cur2.connection.commit()

    except Exception as e:
        print(e)


