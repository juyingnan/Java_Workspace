#include <dlfcn.h>
#include <assert.h>
#include <dirent.h>
#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <map>

using namespace std;
//����ڵ�
struct Node;
typedef struct Node* PNode;

struct Node
{
	int docNum;
	PNode next;
};
typedef struct Node* DocNode;

vector<DocNode> copy(vector<DocNode> nodes);

//�����ڵ�
DocNode createDocNode(int value)
{
	vector<DocNode> nodes;
	cout<<nodes.size()<<endl;

	DocNode node = (DocNode)malloc(sizeof(struct Node));
	//DocNode* node = new DocNode();
	if(node!=NULL)
	{
		node->next = NULL;
		node->docNum = value;
	}
	else cout<<"Out of space!\n"<<endl;
	return node;
}

//���ҽڵ��Ƿ���� -1 ������ 1 ����
int valueExist(DocNode node,int docNum)
{
	DocNode x = node;
	while(x!=NULL && x->docNum != docNum)
	{
		x = x->next;
	}
	if(x==NULL) return -1;
	else return 1;
}

//����һ��ֵ��ȡһ���ڵ�,ͬʱ����ڵ�ᱻԭ�ڵ��б����Ƴ�
DocNode getNode(DocNode  node, int docNum)
{
	//node�п���Ϊ��!
	if(node == NULL) return NULL;
	DocNode pre = node;
	DocNode x = node;
	while(x!=NULL && x->docNum != docNum)
	{
		x = x->next;
		//��һ��pre���ƶ�
		//�ӵڶ��ο�ʼ,pre�ƶ�
		if(pre->next!=x)
		{
			pre= pre->next;
		}
	}
	//û���ҵ����
	if(x==NULL) return NULL;
	else
	{	
		//�ҵ�������������:
		//1.�����ͷ��㲢��û����һ���ڵ���(������ֻ��һ���ڵ�)
		if(x==node && x->next == NULL)
		{
			return x;
		}
		//2.���������x��ͷ���,���Ұ�����һ��
		else if(x==node && x->next!=NULL)
		{
			pre = x->next;
			x->next = NULL;
			return x;
		}
		else
		{			
			//3.�ҵ����,���ڵ�Ͽ�����
			pre->next = x->next;
			x->next = NULL;
			return x;
		}
	}
}


//��Ϊ���в������ǽ���������Ĳ�������֮��,���Ա��������������һ��,�Է�ֹ�ƻ�ԭ�еĽṹ
vector<DocNode> copy(vector<DocNode> nodes)
{
	vector<DocNode> result;
	cout<<"nodes.size()="<<nodes.size()<<"Ok"<<endl;
	for(int i =0; i <nodes.size(); i++ )
	{
		//����ͷ�ڵ�
		DocNode head = nodes[i];		
		cout<<"nodes["<<i<<"]->docNum = "<<head->docNum<<" OK!"<<endl;
		DocNode node = createDocNode(head->docNum);
		cout<<"Copy head "<<i<<" OK!"<<endl;
		//׼����Ҫ�ƶ���ָ��
		DocNode temp = node;
		DocNode probe = head->next;
		//��ʼ����
		while(probe!=NULL)
		{
			temp->next =  createDocNode(probe->docNum);
			temp = temp->next;
			probe = probe->next;
		}
		//�������б�
		result.push_back(node);
	}
	cout<<"result.size()="<<result.size()<<"Ok"<<endl;
	return result;
}




//��ʾһ������
void displayDocList(DocNode list)
{
	DocNode node = list;
	while(node->next!=NULL)
	{
		cout<<node->docNum<<" ";
		node=node->next;
	}
	cout<<node->docNum<<endl;
}


int main(int argc , char** argv)
{
	cout<<"Copy Test"<<endl;
	vector<DocNode> nodes;
	
	DocNode node1=createDocNode(1);
	DocNode node2=createDocNode(2);
	DocNode node3=createDocNode(3);
	DocNode node4=createDocNode(4);
	DocNode node5=createDocNode(5);
	DocNode node6=createDocNode(6);
	DocNode node7=createDocNode(7);
	DocNode node8=createDocNode(8);

	node1->next = node2;
	node2->next = node3;
	node3->next = node4;

	node5->next = node6;
	node6->next = node7;
	node7->next = node8;

	nodes.push_back(node1);
	nodes.push_back(node5);
	
	vector<DocNode> newnode = copy(nodes);

	cout<<newnode.size()<<endl;
	displayDocList(newnode[0]);
	displayDocList(newnode[1]);
	
	node6->next = NULL;

	displayDocList(nodes[0]);
	displayDocList(nodes[1]);

	displayDocList(newnode[0]);
	displayDocList(newnode[1]);

	return 0;
}
