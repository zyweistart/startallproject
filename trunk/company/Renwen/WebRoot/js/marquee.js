marqueesHeight=208; //�������߶�
stopscroll=false; //������������Ƿ�ֹͣ����
with(marquees){
noWrap=true; //�������������Զ�����
style.width=0; //�������ǿ��Խ����Ŀ����Ϊ0����Ϊ���ᱻ�Ŵ�
style.height=marqueesHeight;
style.overflowY="hidden"; //���������ɼ�
onmouseover=new Function("stopscroll=true"); //��꾭����ֹͣ����
onmouseout=new Function("stopscroll=false"); //����뿪����ʼ����
}
//��ʱ���������ĸ߶����޷���ȡ�ˡ��������һ�����ɼ��Ĳ�"templayer"���Ժ����ݸ��Ƶ����棺
document.write('<div id="templayer" style="position:absolute;z-index:1;visibility:hidden"></div>');
function init(){ //��ʼ����������
//��θ���ԭ���ݵ�"templayer"��ֱ��"templayer"�ĸ߶ȴ����������߶ȣ�
while(templayer.offsetHeight<marqueesHeight){
templayer.innerHTML+=marquees.innerHTML;
} //��"templayer"�����ݵġ����������ƻ�ԭ��������
marquees.innerHTML=templayer.innerHTML+templayer.innerHTML;
//����������ʱ������"scrollUp()"����������������
setInterval("scrollUp()",50);
}
document.body.onload=init;
preTop=0; //������������жϹ������Ƿ��Ѿ����˾�ͷ
function scrollUp(){ //����������������
if(stopscroll==true) return; //�������"stopscroll"Ϊ�棬��ֹͣ���� 
preTop=marquees.scrollTop; //��¼����ǰ�Ĺ�����λ��
marquees.scrollTop+=1; //�����������ƶ�һ������
//��������������ˣ������Ϲ������͵�ǰ����һ����λ��
//��Ȼ������ˣ�ͬ����Ҫ���¹���һ������(+1)��
if(preTop==marquees.scrollTop){
marquees.scrollTop=templayer.offsetHeight-marqueesHeight+1;
}
}
