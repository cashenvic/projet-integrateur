import { ApplicationError } from './application-error';
import { AjaxError, AjaxRequest } from 'rxjs/ajax';

describe('ApplicationError', () => {
  it('should create an instance', () => {
    expect(new ApplicationError('')).toBeTruthy();
  });

  it('new with message', () => {
    const message = '...';
    const errorAppli = new ApplicationError(message);

    expect(errorAppli.message).toBe(message);
    expect(errorAppli.innerError).toBeNull();
  });

  it('new without message', () => {
    const errorAppli = new ApplicationError(null);

    expect(errorAppli.message).toBe('');
    expect(errorAppli.innerError).toBeNull();
  });

  it('wrappeError with message', () => {
    const messageApp = 'erreur';
    const message = '...';
    const errorAppli = ApplicationError.wrappeError(new Error(message), messageApp);

    expect(errorAppli.message).toBeDefined();
    expect(errorAppli.innerError).toBeDefined();
    expect(errorAppli.innerError.message).toBe(message);
  });

  it('wrappeError with messages', () => {
    const messageApp = 'erreur';
    const message = '...';
    const messageForUser = '...';
    const errorAppli = ApplicationError.wrappeError(new Error(message), messageApp, messageForUser);

    expect(errorAppli.messageForUser).toBe(messageForUser);
  });

  it('wrappeError without message', () => {
    const message = '...';
    const errorAppli = ApplicationError.wrappeError(new Error(message));

    expect(errorAppli.message).toBe(message);
    expect(errorAppli.innerError).toBeDefined();
    expect(errorAppli.innerError.message).toBe(message);
  });

  it('innerErrors 1 level', () => {
    const errorAppli = new ApplicationError(null);

    const errors = ApplicationError.getErrors(errorAppli);
    expect(errors).toBeDefined();
    expect(errors.length).toBe(1);
    expect(errors[0]).toBe(errorAppli);
  });

  it('innerErrors 2 levels avec 1 seule encapsulation', () => {
    const message = 'erreur';
    const error = new Error(message);
    // Il n'y aura qu'une seule encapsulation puisque rien ne change
    const errorAppli = ApplicationError.wrappeError(ApplicationError.wrappeError(error));

    const errors = ApplicationError.getErrors(errorAppli);
    expect(errors).toBeDefined();
    expect(errors.length).toBe(2);
    expect(errors[0]).toBe(error);
  });

  it('innerErrors 3 levels', () => {
    const message = 'erreur';
    const error = new Error(message);
    // Il y aura une 2ème encapsulation puisque on défini un message
    const errorAppli = ApplicationError.wrappeError(ApplicationError.wrappeError(error), 'message');

    const errors = ApplicationError.getErrors(errorAppli);
    expect(errors).toBeDefined();
    expect(errors.length).toBe(3);
    expect(errors[0]).toBe(error);
  });

  it('wrappeAjax with message', () => {
    const message = '...';
    const messageAppli = '_';
    const error = new AjaxError(message, new XMLHttpRequest(), {} as AjaxRequest);
    const errorAppli = ApplicationError.wrappeAjax(messageAppli, error);

    expect(errorAppli.message).toBeDefined();
    expect(errorAppli.innerError).toBeDefined();
    expect(errorAppli.innerError.message).toBe(message);
  });

  it('wrappeAjax without message', () => {
    const message = '...';
    const error = new AjaxError(message, new XMLHttpRequest(), {} as AjaxRequest);
    const errorAppli = ApplicationError.wrappeAjax(null, error);

    expect(errorAppli.message).toBeDefined();
    expect(errorAppli.innerError).toBeDefined();
    expect(errorAppli.innerError.message).toBe(message);
  });
});
